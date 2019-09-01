/**
 * 权限管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;

        tableIns=table.render({
            id:'id',
            elem: '#permissionList',
            url:'/permission/permissionList',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type:'numbers'/*,width:"5%"*/}
                /*,{field:'id', title:'id'}
                ,{field:'parentId', title:'parentId'}*/
                ,{field:'pname', title:'父级菜单',align:'center'/*,width:"10%"*/}
                ,{field:'name', title:'菜单名称',align:'center'/*,width:"10%"*/}
                ,{field:'descpt', title:'描述',align:'center'/*,width:"15%"*/}
                ,{field:'url', title:'菜单url',align:'center'/*,width:"15%"*/}
                ,{field:'createTime', title:'创建时间',align:'center'/*,width:"10%"*/}
                ,{field:'updateTime', title:'更新时间',align:'center'/*,width:"10%"*/}
                ,{fixed:'right',title:'操作',align:'center', toolbar:'#optBar'/*,width:"25%"*/}
            ]],
            done: function(res, curr, count){
                $("[data-field='pname']").children().each(function(){
                    if($(this).text()==''){
                        $(this).text("根目录");
                    }else {
                        $(this).text($(this).text());
                    }
                });
                pageCurr=curr;

            }
        });


        //监听工具条
        table.on('tool(permissionTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                del(data,data.id);
            } else if(obj.event === 'edit'){
                //编辑
                edit(data);
            }
        });

        //监听提交
        form.on('submit(permissionSubmit)', function(data){
            formSubmit(data);
            return false;
        });

    });

});

//提交表单
function formSubmit(obj){
    $.ajax({
        type: "post",
        data: $("#permissionForm").serialize(),
        url: "/permission/setPermission",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.msg);
            }
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                load(obj);
            });
        }
    });
}
//新增
function add() {
    edit(null,"新增");
}
//打开编辑框
function edit(data,title){
    var parentId = null;
    if(data == null){
        $("#id").val("");
    }else{
        //回显数据
        $("#id").val(data.id);
        $("#name").val(data.name);
        $("#descpt").val(data.descpt);
        $("#url").val(data.url);
        parentId = data.pid;
    }
    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);
    $.ajax({
        url:'/permission/parentPermissionList',
        dataType:'json',
        async: true,
        success:function(data){
            $("#pid").html("<option value='0'>根目录</option>");
            $.each(data,function(index,item){
                if(!parentId){
                    var option = new Option(item.name,item.id);
                }else {
                    var option = new Option(item.name,item.id);
                    // // 如果是之前的parentId则设置选中
                    if(item.id == parentId) {
                        option.setAttribute("selected",'true');
                    }
                }
                $('#pid').append(option);//往下拉菜单里添加元素
                form.render('select'); //这个很重要
            })
        }
    });

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setPermission'),
        end:function(){
            cleanPermission();
        }
    });
}

function cleanPermission() {
    $("#name").val("");
    $("#descpt").val("");
    $("#url").val("");
}

//重新加载table
function load(obj){
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

//删除
function del(obj,id) {
    if(null!=id){
        layer.confirm('您确定要删除吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/permission/del",{"id":id},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
}