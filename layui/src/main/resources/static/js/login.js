/**
 * 登录
 */
$(function(){
     layui.use(['form' ,'layer'], function() {
         var form = layui.form;
         var layer = layui.layer;
         form.on("submit(login)",function () {
             login();
             return false;
         });
         var path=window.location.href;
         if(path.indexOf("kickout")>0){
             layer.alert("您的账号已在别处登录；若不是您本人操作，请立即修改密码！",function(){
                 window.location.href="/login";
             });
         }
     })
 })

function login(){
    var username=$("#username").val();
    var password=$("#password").val();
    var rememberMe = $("#rememberMe").val();
    $.post("/user/login",$("#useLogin").serialize(),function(data){
        if(data.code == 1){
            window.location.href=data.url;
        }else{
            layer.alert(data.message,function(){
                layer.closeAll();//关闭所有弹框
            });
        }
    });
}
