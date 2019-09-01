layui.define(['form', 'laypage'], function (exports) {
    var form = layui.form,
        Grid = {
            config: {
                render: function (view, data) { return '未配置渲染函数'; }, //渲染函数
                token: ''
            },
            createNew: function (option) {
                var GRIDORDER = [], RESIZE = { left: 0, top: 0 }, CONFIG = this.config;

                //样式
                var CSS = {
                    danger:'grid-danger'
                }

                var grid = {
                    elem: typeof (option.elem) === 'string' ? $('#' + option.elem) : option.elem, //GridID 或 Jquery对象
                    view: option.view,  //模板名称
                    //originData: option.data,
                    data: option.data,  //模板数据（json）
                    pageData: null,  //页面数据，分页后当前页数据
                    url: option.url,    //获取数据的url(返回Json，优先使用data数据)
                    page: option.page || 1, //页码
                    pageSize: option.pageSize, //每页显示条数
                    pageGroup: option.pageGroup == undefined ? 5 : option.pageGroup,
                    search: option.search, //搜索按钮(submit)的lay-filter
                    searchData: option.searchData || [],
                    html: '',   //渲染后的html
                    flag: true,  //获取数据是否成功
                    message: '', //错误消息
                    total: 0,  //数据总条数
                    record: option.record || false,  //是否显示总记录数
                    freeze: option.freeze,
                    singleSelect: option.singleSelect || false, //是否单选
                    success: option.success,  //渲染结束后执行
                    rowClick: option.rowClick,
                    rowid: option.rowid,
                    dataSuccess: option.dataSuccess  //获取数据成功后触发的事件
                };
                grid.build = function () {
                    fnData(fnSuccess);
                }

                function fnFreeze() {
                }

                //获取数据
                function fnData(fn) {
                    grid.loading();
                    if (grid.data) {
                        var DATA = $.extend(true, {}, grid.data);

                        //排序
                        if (GRIDORDER.length > 0) {
                            DATA.rows.sort(function (a, b) {
                                var r = 0;
                                for (var i = GRIDORDER.length; i > 0; i--) {
                                    var x = a[GRIDORDER[i - 1].code],
                                        y = b[GRIDORDER[i - 1].code];
                                    if (x == y) r = 0;
                                    else {
                                        r = (x > y ? 1 : -1) * (GRIDORDER[i - 1].asc == 'asc' ? 1 : -1);
                                        break;
                                    }
                                }
                                return r;
                            });
                        }

                        if (grid.pageSize) {
                            grid.total = grid.data.rows.length;
                            var pageData = $.extend(true, {}, DATA);
                            pageData.rows = pageData.rows.slice(grid.pageSize * (grid.page - 1), grid.pageSize * grid.page);
                            grid.pageData = pageData;
                        } else
                            grid.pageData = DATA;
                        grid.flag = true;
                        if (grid.dataSuccess) grid.dataSuccess(grid.pageData);
                        fn();
                    }
                    else if (grid.url) {
                        var url = grid.url;
                        if (grid.pageSize) {
                            grid.searchData.page = grid.page;
                            grid.searchData.pageSize = grid.pageSize;
                        }
                        //生成排序字符串
                        if (GRIDORDER.length > 0) {
                            var orderStr = '';
                            $.each(GRIDORDER, function (_, order) {
                                orderStr += order.code + ' ' + order.asc + ',';
                            });
                            orderStr = orderStr.substr(0, orderStr.length - 1);
                            grid.searchData.orderString = orderStr;
                        }
                        $.ajax({
                            url: url,
                            data: grid.searchData,
                            type: 'get',
                            dataType: 'json',
                            beforeSend: function (xhr) {
                                if (CONFIG.token) {
                                    xhr.setRequestHeader('Authorization', 'Basic ' + CONFIG.token);
                                }
                            },
                            success: function (data) {
                                grid.pageData = data;
                                grid.flag = data.flag;
                                grid.total = data.total;
                                if (grid.dataSuccess) grid.dataSuccess(data);
                                fn();
                            },
                            error: function (error) {
                                grid.flag = false;
                                fn();
                            }
                        });
                    }
                    else {
                        grid.pageData = { rows: [] };
                        fn();
                    }
                }

                //事件
                var events = {
                    //行点击事件
                    rowClick: function () {
                        var that = $(this);
                        if (that.hasClass('selected')) {
                            if (!grid.singleSelect) {
                                that.removeClass('selected');
                                if (that.find('td:first :checkbox').length > 0)
                                    that.find('td:first :checkbox')[0].checked = false;
                            }
                            if (grid.elem.find('.grid-head').find('th,td').first().find(':checkbox').length > 0)
                                grid.elem.find('.grid-head').find('th,td').first().find(':checkbox')[0].checked = false;
                        } else {
                            that.addClass('selected');
                            if (that.find('td:first :checkbox').length > 0) {
                                that.find('td:first :checkbox')[0].checked = true;
                                that.find('td:first :checkbox').on('click', function () {
                                    that[0].checked = true;
                                });
                            }
                            if (grid.singleSelect)
                                that.siblings().removeClass('selected').each(function () {
                                    if ($(this).find('td:first :checkbox').length > 0)
                                        $(this).find('td:first :checkbox')[0].checked = false;
                                });
                        }
                        if (grid.rowClick)
                            grid.rowClick(grid.pageData.rows[that.index()]);
                        that.addClass('focus');
                        that.siblings().removeClass('focus');
                    }
                    //排序
                    , order: function () {
                        var that = $(this);
                        var ord = that.find('.grid-order');
                        var ordCode = that.attr('order');
                        if (ord.hasClass('grid-order-asc')) {
                            ord.removeClass('grid-order-asc').addClass('grid-order-desc');
                            $.each(GRIDORDER, function (i, item) {
                                if (item.code == ordCode) {
                                    GRIDORDER.splice(i, 1);
                                    return false;
                                }
                            });
                            GRIDORDER.push({ code: ordCode, asc: 'desc' });
                        } else if (ord.hasClass('grid-order-desc')) {
                            ord.removeClass('grid-order-desc');
                            $.each(GRIDORDER, function (i, item) {
                                if (item.code == ordCode) {
                                    GRIDORDER.splice(i, 1);
                                    return false;
                                }
                            });
                        } else {
                            ord.addClass('grid-order-asc');
                            $.each(GRIDORDER, function (i, item) {
                                if (item.code == ordCode) {
                                    GRIDORDER.splice(i, 1);
                                    return false;
                                }
                            });
                            GRIDORDER.push({ code: ordCode, asc: 'asc' });
                        }
                        fnData(function () {
                            fnBody(CONFIG.render(grid.view, grid.pageData), grid.elem.find('.grid-content'));
                        });
                    }
                    //全选
                    , selectAll: function () {
                        var that = $(this);
                        var checked = that[0].checked,
                            trs = grid.elem.find('.grid-body tbody tr');
                        checked ? trs.addClass('selected') : trs.removeClass('selected');
                        trs.each(function () {
                            if ($(this).find('td:first :checkbox').length > 0)
                                $(this).find('td:first :checkbox')[0].checked = checked;
                        });
                    }
                }


                //渲染Grid视图
                function fnSuccess() {
                    grid.elem.html('');
                    var content = $('<div class="grid-content"></div>').appendTo(grid.elem),
                           html = CONFIG.render(grid.view, grid.pageData);
                    fnHeader(html, content);
                    fnBody(html, content);

                    if (grid.success)
                        grid.success(grid.pageData, grid.elem);

                    setTimeout(function () {
                        grid.resize();
                    }, 100);
                    setTimeout(function () {
                        grid.resize();
                    },200)
                    $(window).resize(function () {
                        if (grid.elem.parents('div.layui-tab-item').length > 0) {
                            if (grid.elem.parents('div.layui-tab-item').hasClass('layui-show')) {
                                grid.resize();
                            }
                        }
                        else
                            grid.resize();
                    });
                }


                //header 处理
                function fnHeader(html, content) {
                    var tbHead = content.find('.grid-head');
                    if (tbHead.length == 0)
                        tbHead = $('<div class="grid-head"></div>').appendTo(content);
                    tbHead.html(html);
                    tbHead.find('>table').nextAll().remove();
                    tbHead.find('thead').siblings().empty();

                    tbHead.find('thead th,thead td').each(function (i) {
                        var that = $(this);
                        /*if (typeof (that.attr('order')) != 'undefined') {
                            that.append('<i class="grid-order"></i>').on('click', events.order);
                        }

                        if (i == 0 && $(this).find(':checkbox').length > 0 && !grid.singleSelect) {
                            that.find(':checkbox').on('click', events.selectAll);
                        }*/

                        that.html('<div class="grid-coll">' + that.html() + '</div>');
                        if (typeof (that.attr('order')) != 'undefined') {
                            that.on('click', events.order).find('.grid-coll').append('<i class="grid-order"></i>');
                        }
                        if (i == 0 && that.find(':checkbox').length > 0 && !grid.singleSelect) {
                            that.find(':checkbox').on('click', events.selectAll);
                        }
                    });
                }

                //body处理
                function fnBody(html, content) {
                    if (!grid.flag) {
                        content.addClass('grid-err').html('获取数据失败');
                        grid.closeLoading();
                        return;
                    }
                    var tbHead = content.find('.grid-head').find('table'),
                        tbBody = content.find('.grid-body');
                    if (tbBody.length == 0)
                        tbBody = $('<div class="grid-body"></div>').appendTo(content);
                    tbBody.html(html);
                    tbBody.find('>table').prevAll().remove();

                    tbBody.find('thead th,thead td').each(function () {
                        /*if (typeof ($(this).attr('order')) != 'undefined')
                            $(this).append('<i class="grid-order"></i>');*/

                        var that = $(this);
                        that.html('<div class="grid-coll">' + that.html() + '</div>');
                        if (typeof (that.attr('order')) != 'undefined') {
                            that.find('.grid-coll').append('<i class="grid-order"></i>');
                        }

                    });
                    if (tbBody.find('tbody tr').length == 0)
                        tbBody.find('tbody').append('<tr><td style="border:none"></td></tr>');

                    var tool = tbBody.find('>.grid-tool').remove();
                    grid.elem.find('.grid-page').remove();
                    var pageDom = $('<div class="grid-page"><div></div></div>');
                    var pages = Math.ceil(grid.total / grid.pageSize);
                    if (pages > 1 || tool.length > 0) {
                        pageDom.appendTo(content);
                        tool.prependTo(pageDom);
                    }
                    if (pages > 1) {
                        layui.laypage({
                            record: grid.record ? grid.total : 0,
                            cont: pageDom.find('>div'),
                            pages: pages,
                            curr: grid.page,
                            groups: grid.pageGroup,
                            skip: true,
                            first: 1,
                            last: pages,
                            prev: '<em><</em>',
                            next: '<em>></em>',
                            jump: function (obj, isFirst) {
                                if (!isFirst) {
                                    grid.page = obj.curr;
                                    //grid.build();
                                    fnData(function () {
                                        fnBody(CONFIG.render(grid.view, grid.pageData), grid.elem.find('.grid-content'));
                                    });
                                }
                            }
                        });
                    }

                    var trs = tbBody.find('tbody tr');
                    if (trs.length > 0) {
                        var checkbox = tbHead.find('th').first().find(':checkbox');

                        trs.on('click', events.rowClick);
                    }
                    tbBody.scroll(function () {
                        RESIZE = {
                            left: $(this).scrollLeft(),
                            top: $(this).scrollTop()
                        };
                        tbHead.css('margin-left', -RESIZE.left);
                    });
                    grid.closeLoading();

                    grid.resize();

                    //verify(tbBody);
                }

                grid.loading = function () {
                    var loading = grid.elem.find('.loading_bg,.loading');
                    if (loading.length == 0)
                        loading = $('<div class="loading_bg"></div><div class="loading"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63d;</i></div>')
                            .appendTo(grid.elem);
                    loading.show();
                }

                grid.closeLoading = function () {
                    grid.elem.find('.loading_bg,.loading').hide();
                }

                grid.getIndex = function () {
                    var tr = this.elem.find('.grid-body tbody tr.focus');
                    if (tr.length > 0)
                        return tr.index();
                    else
                        return -1;
                }

                grid.getRow = function () {
                    var index = grid.getIndex();
                    if (index >= 0)
                        return grid.pageData.rows[index];
                    else
                        return null;
                }

                grid.focus = function (index) {
                    if (index == undefined || index == -1) {
                        grid.elem.find('.grid-body tbody tr.focus').removeClass('focus');
                    }
                    else {
                        var tr = grid.elem.find('.grid-body tbody tr:eq(' + index + ')').addClass('focus');
                        tr.siblings('.focus').removeClass('focus');
                        if (grid.singleSelect)
                            grid.selectIndex(index);
                        else {
                            tr.addClass('selected');
                            if (tr.find('td:first :checkbox').length > 0)
                                tr.find('td:first :checkbox')[0].checked = true;
                        }
                    }
                }

                grid.selectValue = function (v) {
                    var rowid = this.rowid;
                    if (v == undefined) {
                        var rows = this.selectRow();
                        if (rows == null)
                            return null;

                        if (this.singleSelect) {
                            return rows[rowid];
                        } else {
                            values = [];
                            $.each(rows, function (_, row) {
                                values.push(row[rowid]);
                            });
                            return values;
                        }
                    } else {
                        if (this.singleSelect)
                            v = [v];
                        this.elem.find('tbody tr.selected').each(function () {
                            $(this).removeClass('selected');
                            if ($(this).find('td:first :checkbox').length > 0)
                                $(this).find('td:first :checkbox')[0].checked = false;
                        });
                        $.each(grid.pageData.rows, function (i, row) {
                            if (v.indexOf(row[rowid]) != -1) {
                                var tr = grid.elem.find('tbody tr:eq(' + i + ')');
                                tr.addClass('selected');
                                if (tr.find('td:first :checkbox').length > 0)
                                    tr.find('td:first :checkbox')[0].checked = true;
                            }
                        });
                    }
                }

                grid.selectRow = function () {
                    /*if (values != undefined) {
                        grid.elem.find('.grid-body tbody tr.selected').removeClass('selected').each(function () {
                            if ($(this).find('td:first :checkbox').length > 0)
                                $(this).find('td:first :checkbox')[0].checked = false;
                        });
                        var keys = grid.keys.split(';');
                        if (Object.prototype.toString.call(values) == '[object Array]' && !grid.singleSelect) {
                            $.each(values, function (a, value) {
                                $.each(grid.pageData.rows, function (i, r) {
                                    var key = '';
                                    $.each(keys, function (j, k) {
                                        key += r[k]+';';
                                    });
                                    if (value + ';' == key) {
                                        var tr = grid.elem.find('.grid-body tbody tr:eq(' + i + ')');
                                        tr.addClass('selected');
                                        if (tr.find('td:first :checkbox').length > 0)
                                            tr.find('td:first :checkbox')[0].checked = true;
                                        return false;
                                    }
                                });
                            });
                        }
                        else {
                            $.each(grid.pageData.rows, function (i, r) {
                                var key = '';
                                $.each(keys, function (j, k) {
                                    key += r[k]+';';
                                });
                                if (values + ';' == key) {
                                    var tr = grid.elem.find('.grid-body tbody tr:eq(' + i + ')');
                                    tr.addClass('selected');
                                    if (tr.find('td:first :checkbox').length > 0)
                                        tr.find('td:first :checkbox')[0].checked = true;
                                    return false;
                                }
                            });
                        }
                    }
                    else {*/
                    if (!grid.singleSelect) {
                        rows = [];
                        this.elem.find('.grid-body tbody tr').each(function (i) {
                            if ($(this).hasClass('selected'))
                                rows.push(grid.pageData.rows[i]);
                        });
                        return rows.length > 0 ? rows : null;
                    }
                    else {
                        var tr = this.elem.find('.grid-body tbody tr.selected');
                        if (tr.length > 0)
                            return grid.pageData.rows[tr.index()];
                        else
                            return null;
                    }
                    //}
                }

                grid.selectIndex = function (index) {
                    if (index != undefined && index >= 0) {
                        grid.elem.find('.grid-body tbody tr.selected').removeClass('selected').each(function () {
                            if ($(this).find('td:first :checkbox').length > 0)
                                $(this).find('td:first :checkbox')[0].checked = false;
                        });
                        if (Object.prototype.toString.call(index) == '[object Array]' && !grid.singleSelect) {
                            $.each(index, function (i, idx) {
                                var tr = grid.elem.find('.grid-body tbody tr:eq(' + idx + ')');
                                tr.addClass('selected');
                                if (tr.find('td:first :checkbox').length > 0)
                                    tr.find('td:first :checkbox')[0].checked = true;
                                return false;
                            });
                        } else {
                            var tr = grid.elem.find('.grid-body tbody tr:eq(' + index + ')');
                            tr.addClass('selected');
                            if (tr.find('td:first :checkbox').length > 0)
                                tr.find('td:first :checkbox')[0].checked = true;
                        }
                    }
                    else if (index == undefined) {
                        if (!grid.singleSelect) {
                            rows = [];
                            this.elem.find('.grid-body tbody tr').each(function (i) {
                                if ($(this).hasClass('selected'))
                                    rows.push(i);
                            });
                            return rows.length > 0 ? rows : null;

                        } else {
                            var tr = this.elem.find('.grid-body tbody tr.selected');
                            if (tr.length > 0)
                                return tr.index();
                            else
                                return -1;
                        }
                    }
                }

                //生成表格行并绑定事件
                function rowsDom(rows, op) {
                    if (Object.prototype.toString.call(rows) != '[object Array]')
                        rows = [rows];
                    var html = CONFIG.render(grid.view, { rows: rows, op: op });
                    //var content = grid.elem.find('.grid-content');
                    var trs = $(html).find('tbody tr');
                    trs.on('click', events.rowClick);
                    //verify(trs);
                    return trs;
                }

                function verify(elem) {
                    var flag = true, msg = '', errOthis;
                    var fmVer = form.config.verify;
                    elem.find('*[lay-verify]').each(function () {
                        var othis = $(this),
                            td = othis.parents('td').last(),
                            ver = othis.attr('lay-verify').split('|'),
                            tips='',
                            value = othis.val();
                        //othis.removeClass(DANGER);
                        $.each(ver, function (_, thisVer) {
                            var isFn = typeof fmVer[thisVer] === 'function';
                            if (fmVer[thisVer] && (isFn ? tips = fmVer[thisVer](value, othis[0]) : !fmVer[thisVer][0].test(value))) {
                                td.addClass(CSS.danger);
                                flag = false;
                                if (msg == '') msg = tips || fmVer[thisVer][1];
                                if (!errOthis) errOthis = othis;
                            }
                        });
                        $(this).on('keyup', function () {
                            td.removeClass(CSS.danger);
                        });
                    });
                    if (!flag) {
                        layer.msg(msg, { icon: 5, shift: 6 });
                        errOthis.select();
                    }
                    return flag;
                }

                grid.insert = function (rows, index) {
                    if (Object.prototype.toString.call(rows) != '[object Array]')
                        rows = [rows];

                    if (index == undefined || index < 0) {
                        $.each(rows, function (i, row) {
                            grid.pageData.rows.push(row);
                        });

                    } else {
                        var str = JSON.stringify(rows);
                        str = str.substr(1, str.length - 2);
                        if (index == 0)
                            eval('grid.pageData.rows.unshift(' + str + ')');
                        else
                            eval('grid.pageData.rows.splice(' + index + ',0,' + str + ')');
                    }
                    var trs = rowsDom(rows);
                    var tbody = grid.elem.find('.grid-body tbody');
                    if (index == undefined || index < 0)
                        tbody.append(trs);
                    else if (index == 0)
                        tbody.prepend(trs);
                    else {
                        tbody.find('>tr:eq(' + index + ')').before(trs);
                    }
                    grid.resize();
                    return trs;
                }

                grid.update = function (row, index) {
                    if (index == undefined || index < 0) {
                        console.log('grid error: index参数错误');
                        return;
                    }
                    grid.pageData.rows[index] = row;

                    var tr = rowsDom(row);
                    grid.elem.find('.grid-body tbody').find('>tr:eq(' + index + ')').replaceWith(tr);
                    grid.resize();

                    //grid.selectIndex(index);
                    grid.focus(index);
                    return tr;
                }

                grid.delete = function (index) {
                    if (index == undefined) {
                        console.log('grid error:index参数错误');
                        return;
                    } else if (Object.prototype.toString.call(index) != '[object Array]') {
                        index = [index];
                    }
                    for (var i = index.length; i > 0; i--) {
                        grid.pageData.rows.splice(index[i - 1], 1);
                        grid.elem.find('.grid-body tbody').find('>tr:eq(' + index[i - 1] + ')').remove();
                    }
                    grid.resize();
                }

                //更新表格页脚
                grid.foot = function (footData) {
                    var html = CONFIG.render(grid.view, footData);
                    var tfoot = $(html).find('tfoot');
                    grid.elem.find('.grid-body tfoot').replaceWith(tfoot);
                    grid.resize();
                }

                //编辑行
                grid.edit = function (index) {
                    var body = grid.elem.find('.grid-body tbody');
                    //if (index == undefined)
                    //    index = grid.selectIndex();
                    if (index == undefined || index < 0) {
                        var rowhtml = rowsDom(grid.pageData.rows, 'edit');
                        body.html(rowhtml);
                    }
                    else {
                        var row = grid.pageData.rows[index];
                        var rowhtml = rowsDom(row, 'edit');
                        body.find('>tr:eq(' + index + ')').replaceWith(rowhtml);
                        grid.focus(index);
                    }
                    grid.resize();
                }
                //结束编辑行
                grid.endEdit = function (index) {
                    var body = grid.elem.find('.grid-body tbody');
                    //if (index == undefined)
                    //    index = grid.selectIndex();
                    var row = null;
                    if (index == undefined || index < 0) {
                        if (verify(body)) {
                            body.find('tr').each(function (i, tr) {
                                $(tr).find('*[name]').each(function (_, item) {
                                    grid.pageData.rows[i][$(item).attr('name')] = item.value;
                                });
                            });
                            row = grid.pageData.rows;
                            var rowhtml = rowsDom(row);
                            body.html(rowhtml);
                        }
                    }
                    else {
                        //erify()
                        var tr = body.find('tr:eq(' + index + ')');
                        if (verify(tr)) {
                            tr.find('*[name]').each(function (_, item) {
                                grid.pageData.rows[index][$(item).attr('name')] = item.value;
                            });
                            row = grid.pageData.rows[index];
                            var rowhtml = rowsDom(row);
                            body.find('>tr:eq(' + index + ')').replaceWith(rowhtml);
                            grid.focus(index);
                        }
                    }
                    grid.resize();

                    return row;
                }

                grid.clear = function () {
                    grid.data = { rows: [] };
                    grid.build();
                }

                grid.setData = function (data) {
                    grid.data = data;
                    grid.page = 1;
                    grid.build();
                }
                /*New End*/

                grid.resize = function () {
                    //grid.elem.find('.grid-body thead').show();
                    var head = grid.elem.find('.grid-head>table'),
                        body = grid.elem.find('.grid-body>table');
                    var ths = grid.elem.find('.grid-head th,.grid-head td');
                    var tbhs = grid.elem.find('.grid-body thead th,.grid-body thead td');
                    head.css('width', body.width());
                    tbhs.each(function (i, th) {
                        var w = $(th).find('.grid-coll').width();
                        $(ths[i]).find('.grid-coll').css('width', w);
                    });
                    if (grid.elem.hasClass('grid')) {
                        var top = grid.elem.find('.grid-head').height() || 0,
                            bottom = grid.elem.find('.grid-page').height() || 0;
                        grid.elem.find('.grid-body').css({ top: top, bottom: bottom });
                        if (grid.elem.find('.grid-body table').height() < grid.elem.find('.grid-body').height())
                            grid.elem.find('.grid-body').css({ bottom: 'auto' });
                        var gridTop = 0;
                        var prev = grid.elem.prevAll('.grid-position');
                        if (prev.length == 0) prev = grid.elem.prev();
                        if (prev.length > 0) {
                            gridTop = prev.height() + prev.position().top + 5;
                            grid.elem.css('top', gridTop);
                        }
                    }
                    var H = grid.elem.find('.grid-body table:first thead').height() || 0;
                    if (H > 0)
                        grid.elem.find('.grid-body table:first').css('margin-top', -H);
                    if (RESIZE.top != 0)
                        grid.elem.find('.grid-body').scrollTop(RESIZE.top);
                    if (RESIZE.left != 0)
                        grid.elem.find('.grid-body').scrollLeft(RESIZE.left);
                }

                if (grid.search) { //绑定搜索事件
                    form.on('submit(' + grid.search + ')', function (data) {
                        grid.page = 1;
                        grid.searchData = data.field;
                        grid.build();
                    });
                }

                return grid;
            }
        };
    exports('grid', Grid);
    layui.link(layui.cache.base + 'css/grid.css', null, 'gridcss');
});

