/**
 * 修改用户密码
 * */
$(function(){
    layui.use(['form' ,'layer'], function() {
        var form = layui.form;
        //确认修改密码
        form.on("submit(setPwd)",function () {
            setPwd();
            return false;
        });
    })
})
function setPwd(){
    var pwd=$("#pwd").val();
    var isPwd=$("#isPwd").val();
    $.post("/user/setPwd",{"pwd":pwd,"isPwd":isPwd},function(data){
        console.log("data:"+data);
        if(data.code=="1"){
            layer.alert("操作成功",function () {
                layer.closeAll();
                window.location.href="/logout";
            });
        }else{
            layer.alert(data.message,function () {
                layer.closeAll();
            });
        }
    });
}
