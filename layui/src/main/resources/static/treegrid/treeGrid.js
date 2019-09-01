layui.define('grid', function (exports) {
    var Grid = layui.grid;
    var TreeGrid = {
        config: Grid.config,
        createNew: function (option) {
            var grid = Grid.createNew(option);
            grid.pageSize = undefined;
            grid.id = option.id || 'id';
            grid.parentid = option.parentid || 'parentid';
            grid.root = option.root || 0;
            grid.order = option.order || grid.id;
            grid.asc = option.asc || 'asc';
            grid.loadRow = option.loadRow;  //懒加载

            //grid.onCollapse = option.onCollapse;
            //grid.onExpand = option.onExpand;

            grid.dataSuccess = function (pageData) {
                if (option.dataSuccess) option.dataSuccess(pageData);
                var treeRows = [];
                HELPER.treeData(pageData.rows, treeRows, grid.root);
                grid.pageData.rows = treeRows;
            };

            var EXPANDER = '<span class="treegrid-expander"></span>',
                INDENT = '<span class="treegrid-indent"></span>';

            var HELPER = {
                treeData: function (rows, tree, pid) {
                    if (Object.prototype.toString.call(rows) != '[object Array]')
                        rows = [rows];
                    var items = [];
                    $.each(rows, function (_, item) {
                        if (item[grid.parentid] == pid)
                            items.push(item);
                    });
                    items.sort(function (a, b) {
                        var x = a[grid.order],
                            y = b[grid.order];
                        return x == y ? 0 : (x > y ? 1 : -1);
                    });
                    if (items.length > 0) {
                        $.each(items, function (_, item) {
                            tree.push(item);
                            HELPER.treeData(rows, tree, item[grid.id]);
                        });
                    }
                },
                nodeId: function (node) {
                    var template = /treegrid-([A-Za-z0-9_-]+)/;
                    if (template.test(node.attr('class')))
                        return template.exec(node.attr('class'))[1];
                    else
                        return null;
                },
                nodePid: function (node) {
                    var template = /treegrid-parent-([A-Za-z0-9_-]+)/;
                    if (template.test(node.attr('class')))
                        return template.exec(node.attr('class'))[1];
                    else
                        return null;
                },

                //展开
                expanded: function (flag, id) {
                    var trs, tr;
                    if (id != undefined) {
                        tr = grid.elem.find('.grid-body tr.treegrid-' + id);
                        tr.removeClass('treegrid-collapse').addClass('treegrid-expanded');
                        trs = tr.nextAll();
                    } else
                        trs = grid.elem.find('.grid-body tbody tr');
                    trs.each(function (_, t) {
                        t = $(t);
                        if (tr && t.find('span.treegrid-indent,span.treegrid-expander').length <= tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                            return false;
                        }
                        var pid = HELPER.nodePid(t),
                            pNode = t.prevAll('.treegrid-' + pid);
                        if (flag || pNode.hasClass('treegrid-expanded') || id == undefined && HELPER.nodeId(pNode) == grid.root)
                            t.show();
                        if (t.hasClass('treegrid-collapse') && flag || id == undefined && pid == null)
                            t.removeClass('treegrid-collapse').addClass('treegrid-expanded');
                    });
                    grid.resize();
                },

                //折叠
                cellapsed: function (flag, id) {
                    var trs, tr;
                    if (id != undefined) {
                        tr = grid.elem.find('.grid-body tbody tr.treegrid-' + id);
                        tr.removeClass('treegrid-expanded').addClass('treegrid-collapse');
                        trs = tr.nextAll();
                    } else
                        trs = grid.elem.find('.grid-body tbody tr');
                    trs.each(function (_, t) {
                        t = $(t);
                        if (tr && t.find('span.treegrid-indent,span.treegrid-expander').length <= tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                            return false;
                        }
                        var pid = HELPER.nodePid(t);
                        if (pid != null)
                            t.hide();
                        if (t.hasClass('treegrid-expanded') && (flag) || id == undefined && pid == null)
                            t.removeClass('treegrid-expanded').addClass('treegrid-collapse');
                    });
                    grid.resize();
                },

                //节点展开、折叠事件
                onExpand: function () {
                    var tr = $(this).parents('tr').last(),
                        id = HELPER.nodeId(tr);
                    if (tr.hasClass('treegrid-expanded')) {
                        HELPER.cellapsed(false, id);
                    } else {
                        HELPER.expanded(false, id);
                    }
                    event.stopPropagation(); //阻止冒泡 rowClick
                },

                //懒加载事件
                onLoadRow: function () {
                    var that = $(this);
                    var tr = that.parents('tr').last().removeClass('treegrid-collapse').addClass('treegrid-loading'),
                        id = HELPER.nodeId(tr);
                    that.unbind('click');
                    /*grid.loadRow(id, function (rows) {
                        console.log(123);
                        grid.insertChild(rows, id);
                        that.on('click', HELPER.onExpand);
                        tr.removeClass('treegrid-loading').addClass('treegrid-expanded');
                    });*/
                    grid.loadRow({
                        pid: id,
                        children: function (rows) {
                            tr.removeClass('treegrid-loading');
                            if (!rows) {
                                tr.addClass('treegrid-wrong');
                            }
                            else if (rows.length > 0) {
                                grid.insertChild(rows, id);
                                that.on('click', HELPER.onExpand);
                                tr.addClass('treegrid-expanded');
                            } else {
                                that.replaceWith(INDENT);
                            }
                        }
                    });
                },

                treeDom: function (trs, rows) {
                    if (Object.prototype.toString.call(rows) != '[object Array]')
                        rows = [rows];
                    $.each(trs, function (i, tr) {
                        tr = $(tr),
                        id = rows[i][grid.id],
                        pid = rows[i][grid.parentid];
                        tr.addClass('treegrid-' + id);
                        if (pid != grid.root) tr.addClass('treegrid-parent-' + pid);

                        //多选事件绑定
                        if (!grid.singleSelect) {
                            tr.click(function () {
                                var selected = $(this).hasClass('selected');
                                private_childSelect($(this), selected);
                                var _pid = HELPER.nodePid($(this));
                                if (_pid != null)
                                    private_parentSelect(selected, _pid);
                            });
                        }
                    });

                    function private_childSelect($tr, selected) {
                        $tr.nextAll().each(function (_, tr) {
                            if ($(tr).find('span.treegrid-indent,span.treegrid-expander').length <= $tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                                return false;
                            }
                            selected ? $(tr).addClass('selected') : $(tr).removeClass('selected');
                            if ($(tr).find('td:first :checkbox').length > 0)
                                $(tr).find('td:first :checkbox')[0].checked = selected;
                        });
                    }
                    function private_parentSelect(selected, pid) {
                        var tr = grid.elem.find('tr.treegrid-' + pid);
                        if (tr.length > 0) {
                            if (selected && !tr.hasClass('selected')) {
                                tr.addClass('selected');
                                if (tr.find('td:first :checkbox').length > 0)
                                    tr.find('td:first :checkbox')[0].checked = true;
                            }
                            else if (tr.nextAll('tr.treegrid-parent-' + pid + '.selected').length == 0) {
                                tr.removeClass('selected');
                                if (tr.find('td:first :checkbox').length > 0)
                                    tr.find('td:first :checkbox')[0].checked = false;
                            }
                            pid = HELPER.nodePid(tr);
                            if (pid != null)
                                private_parentSelect(selected, pid);
                        }
                    }


                    $.each(trs, function (_, tr) {
                        tr = $(tr);
                        var id = HELPER.nodeId(tr),
                            pid = HELPER.nodePid(tr);
                        if (tr.next('.treegrid-parent-' + id).length > 0) {
                            $(EXPANDER).on('click', HELPER.onExpand).prependTo(tr.addClass('treegrid-expanded').find('td:eq(0)'));
                        } else if (grid.loadRow && grid.pageData.rows[tr.index()]._children) {
                            $(EXPANDER).on('click', HELPER.onLoadRow).prependTo(tr.addClass('treegrid-collapse').find('td:eq(0)'));
                        } else {
                            tr.find('td:eq(0)').prepend(INDENT);
                        }
                        if (grid.elem.find('tr.treegrid-' + pid).length > 0) {
                            var prevIndentLen = grid.elem.find('tr.treegrid-' + pid).find('.treegrid-expander').prevAll().length;
                            var pindent = '';
                            for (var i = 0; i < prevIndentLen; i++) {
                                pindent += INDENT;
                            }
                            tr.find('td:eq(0)').prepend(pindent + INDENT);
                        }
                    });
                    grid.resize();
                }
            }

            grid.success = function () {
                if (option.success)
                    option.success();
                HELPER.treeDom(grid.elem.find('.grid-body tbody tr'), grid.pageData.rows);

                /*
                if (option.onCollapse) {
                    
                }
                if (option.onExpand) {
                    
                }*/
            };

            //折叠
            grid.collapse = function (id) {
                HELPER.cellapsed(false, id);
            }

            //折叠全部
            grid.collapseAll = function (id) {
                HELPER.cellapsed(true, id);
            }

            //展开
            grid.expand = function (id) {
                HELPER.expanded(false, id);
            }

            //展开全部
            grid.expandAll = function (id) {
                HELPER.expanded(true, id);
            }

            //获取子节数据
            grid.getChild = function (pid) {
                var rows = [],
                    tr = grid.elem.find('.grid-body tr.treegrid-' + pid),
                    index = tr.index();
                $.each(tr.nextAll('.treegrid-parent-' + pid), function () {
                    rows.push(grid.pageData.rows[$(this).index()]);
                });
                return rows;
            }

            //获取子节点数据，包含子节点的延伸
            grid.getChildAll = function (pid) {
                var rows = [],
                    tr = grid.elem.find('.grid-body tr.treegrid-' + pid),
                    index = tr.index();
                $.each(tr.nextAll(),function(i,t){
                    var t=$(t);
                    if (t.find('span.treegrid-indent,span.treegrid-expander').length <= tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                        return false;
                    }
                    rows.push(grid.pageData[index + i]);
                });
                return rows;
            }

            //添加同级节点
            grid.insertNode = function (rows, pid) {
                pid = pid != undefined ? pid : grid.root;
                var index = undefined;
                if (pid != grid.root) {
                    var tr = grid.elem.find('.grid-body tr.treegrid-' + pid);
                    index = tr.index();
                    pid = HELPER.nodePid(tr);
                }

                var newRow = [];
                HELPER.treeData(rows, newRow, pid);
                var trs = grid.insert(newRow, index);
                HELPER.treeDom(trs, newRow);
            }

            //添加子节点
            grid.insertChild = function (rows, pid) {
                pid = pid != undefined ? pid : grid.root;
                var index = undefined;
                if (pid != grid.root) {
                    var tr = grid.elem.find('.grid-body tr.treegrid-' + pid);
                    index = tr.index();
                    index++;
                    tr.addClass('treegrid-expanded');
                    if (tr.find('td').first().find('span.treegrid-expander').length == 0) {
                        tr.find('td').first().find('span.treegrid-indent').last().removeClass('treegrid-indent').addClass('treegrid-expander').on('click', HELPER.onExpand);
                    } else {
                        $.each(tr.nextAll(), function (i, t) {
                            var t = $(t);
                            if (t.find('span.treegrid-indent,span.treegrid-expander').length <= tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                                return false;
                            }
                            index++;
                            if (HELPER.nodePid(t) == pid) t.show();
                        });
                    }
                }
                if (index >= grid.pageData.rows.length)
                    index = undefined;
                var newRow = [];
                HELPER.treeData(rows, newRow, pid);

                var trs = grid.insert(newRow, index);
                HELPER.treeDom(trs, newRow);
            }

            //修改节点
            grid.updateNode = function (row,id) {
                if (id == undefined) {
                    console.log('treegrid error:参数id错误');
                    return;
                }
                var tr = grid.elem.find('.grid-body tr.treegrid-' + id);
                var index = tr.index();
                var trs = grid.update(row, index);
                HELPER.treeDom(trs, row);
            }

            //删除节点及其子节点
            grid.deleteNode = function (id) {
                if (id == undefined) {
                    console.log('treegrid error:参数id错误');
                    return;
                }
                var tr = grid.elem.find('.grid-body tr.treegrid-' + id);
                var index = tr.index(),
                    idx = [index];
                $.each(tr.nextAll(), function (i, t) {
                    var t = $(t);
                    if (t.find('span.treegrid-indent,span.treegrid-expander').length <= tr.find('span.treegrid-indent,span.treegrid-expander').length) {
                        return false;
                    }
                    index++;
                    idx.push(index);
                });
                grid.delete(idx);
            }

            grid.editNode = function (id) {
                var index, trs, rows;
                if (id != undefined) {
                    var tr = grid.elem.find('.grid-body tbody tr.treegrid-' + id);
                    index = tr.index();
                }
                grid.edit(index);
                if (index != undefined && index >= 0) {
                    trs = grid.elem.find('.grid-body tbody tr:eq(' + index + ')');
                    rows = grid.pageData.rows[index];
                } else {
                    trs = grid.elem.find('.grid-body tbody tr');
                    rows = grid.pageData.rows;
                }
                HELPER.treeDom(trs, rows);
            }
            grid.endEditNode = function (id) {
                var index, trs, rows;
                if (id != undefined) {
                    var tr = grid.elem.find('.grid-body tbody tr.treegrid-' + id);
                    index = tr.index();
                }
                grid.endEdit(index);
                if (index != undefined && index >= 0) {
                    trs = grid.elem.find('.grid-body tbody tr:eq(' + index + ')');
                    rows = grid.pageData.rows[index];
                } else {
                    trs = grid.elem.find('.grid-body tbody tr');
                    rows = grid.pageData.rows;
                }
                HELPER.treeDom(trs, rows);
            }

            return grid;
        }
    };
    exports('treegrid', TreeGrid);
    layui.link(layui.cache.base + 'css/grid.css', null, 'gridcss');
});

