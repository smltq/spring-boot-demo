layui.use('form', function(){
    var form = layui.form
	
    //自定义验证规则
    form.verify({
    	qudaoName: [/^(?!_)(?!.*?_$)[_a-zA-Z0-9_\u4e00-\u9fa5]{2,20}$/, '请输入正确格式！'],
   	  	qudaoEmail: function(value, item){ //value：表单的值、item：表单的DOM对象
   		    if(value != null && value != ""){
       		    if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value)){
       		       return '请输入正确格式！';
       		    }
   		    }
   		  },
   		reason: function(value, item){ //value：表单的值、item：表单的DOM对象
   		    if(value != null && value != ""){
       		    if(!/^(?!_)(?!.*?_$)[,，\.。\-、；;\：:\"“\'！!_a-zA-Z0-9_\u4e00-\u9fa5]{1,50}$/.test(value)){
       		       return '请输入正确格式！';
       		    }
   		    }
   		  }
    });
});

var ValidateUtils = {
    checkMobile:function(umobile){
        if(umobile==''|| $.trim(umobile).length==0){
            return "请输入手机号 ";
        }
        if($.trim(umobile).length!=11 || !umobile.match(/^1\d{10}$/)){
            $("input[name='mobile']").val("");
            $("input[name='mobile']").focus();
            return "您输入的手机号格式有误，请重新输入";
        }
        return "ok";
    },
    checkPassword : function(password){
        if(password==''|| $.trim(password).length==0){
            $("input[name='password']").val("");
            $("input[name='password']").focus();
            return "请输入密码";
        }
        if(!password.match(/^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)[^\u4e00-\u9fa5]\S{6,16}$/)){
            $("input[name='password']").val("");
            $("input[name='password']").focus();
            return 	"密码由大写字母、小写字母、数字和字符，两种或两种以上6-16位组成";
        }
        return "ok";
    },
    checkSimplePassword : function(password){
        if(!password.match(/^[0-9_a-zA-Z]{6,20}$/)){
            $("input[name='password']").val("");
            $("input[name='password']").focus();
            return 	"密码格式不正确！";
        }
        return "ok";
    },
    checkCode : function(code){
        if(code==''|| $.trim(code).length==0){
            $("input[name='smsCode']").val("");
            $("input[name='smsCode']").focus();
            return "请您输入验证码";
        }
        if(!code.match(/^[0-9]{6}$/)){
            $("input[name='smsCode']").val("");
            $("input[name='smsCode']").focus();
            return 	"您输入的验证码格式有误，请重新输入";
        }
        return "ok";
    },
    checkPicCode : function(code){
        if(code==''|| $.trim(code).length==0){
            $("input[name='code']").val("");
            $("input[name='code']").focus();
            return "请您输入验证码";
        }
        if(!code.match(/\w{4}$/)){
            $("input[name='code']").val("");
            $("input[name='code']").focus();
            return 	"您输入的验证码格式有误，请重新输入";
        }
        return "ok";
    },
    checkUserName :function(username){
        if(username==''|| $.trim(username).length==0){
            return "用户名不能为空，请重新填写";
        }
        if(!username.match(/^(?!_)(?!.*?_$)[,，\.。\-、；;\：:\"“\'！!_a-zA-Z0-9_\u4e00-\u9fa5]{2,25}$/)){
            $("input[name='userName']").val("");
            $("input[name='userName']").focus();
            return "您输入的用户名格式有误，请重新输入！";
        }
        return "ok";
    },
    checkRoleCode :function(code){
        if(code==''|| $.trim(code).length==0){
            return "角色编号不能为空，请重新填写";
        }
        if(!code.match(/^(?!_)(?!.*?_$)[,，\.。\-、；;\：:\"“\'！!_a-zA-Z0-9_\u4e00-\u9fa5]{2,25}$/)){
            $("input[name='userName']").val("");
            $("input[name='userName']").focus();
            return "您输入的角色编号格式有误，请重新输入！";
        }
        return "ok";
    },
    checkRoleName :function(roleName){
        if(roleName==''|| $.trim(roleName).length==0){
            return "角色名称不能为空，请重新填写";
        }
        if(!roleName.match(/^(?!_)(?!.*?_$)[,，\.。\-、；;\：:\"“\'！!_a-zA-Z0-9_\u4e00-\u9fa5]{2,25}$/)){
            $("input[name='userName']").val("");
            $("input[name='userName']").focus();
            return "您输入的角色名称格式有误，请重新输入！";
        }
        return "ok";
    },
    checkTitle :function(title){
        if(title==''|| $.trim(title).length==0){
            return "标题不能为空，请重新填写";
        }
        if(!title.match(/^(?!_)(?!.*?_$)[,，\.。\-、；;\：:\"“\'！!_a-zA-Z0-9_\u4e00-\u9fa5]{1,30}$/)){
            $("input[name='userName']").val("");
            $("input[name='userName']").focus();
            return "您输入的标题格式有误，请重新输入！";
        }
        return "ok";
    },
    checkCardID:function(cardID){
        if(cardID==''|| $.trim(cardID).length==0){
            return "请输入身份证号 ";
        }
        if($.trim(cardID).length!=18 && $.trim(cardID).length!=15){
            $("input[name='inputCardID']").val("");
            $("input[name='inputCardID']").focus();
            return "您输入的身份证号有误，请重新输入";
        }
        ///^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/
        if(!cardID.match(/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/)){
            $("input[name='inputCardID']").val("");
            $("input[name='inputCardID']").focus();
            return "您输入的身份证号有误，请重新输入";
        }
        return "ok";
    },
    checkBankCode:function(bankcode){
        if(bankcode==''|| $.trim(bankcode).length==0){
            return "请输入银行卡号 ";
        }
        if($.trim(bankcode).length!=19 && $.trim(bankcode).length!=16){
            $("#bankcode").val("");
            $("#bankcode").focus();
            return "您输入的银行卡号有误";
        }
        if(!bankcode.match(/(([1-9])[\d]{18})|([1-9])[\d]{15}/)){
            $("#bankcode").val("");
            $("#bankcode").focus();
            return "您输入的银行卡号有误";
        }
        return "ok";
    },
    checkQQ:function(iqq){
        if(iqq==''|| $.trim(iqq).length==0){
            return "请输入QQ信息 ";
        }
        if(!iqq.match(/^[1-9][0-9]{4,12}/)){
            $("input[name='iqq']").val("");
            $("input[name='iqq']").focus();
            return "您输入的QQ格式有误，请重新输入！";
        }
        return "ok";
    },
    checkEmail:function(email){
        if(email==''|| $.trim(email).length==0){
            return "请输入邮箱 ";
        }
        ///^(\w)+(\.\w+)*@(\w)+((\.\w{2,3}){1,3})$/
        //return !email.match(/^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$/);
        if(!email.match(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/)){
            $("input[name='email']").val("");
            $("input[name='email']").focus();
            return "您输入的邮箱格式有误，请重新输入！";
        }
        return "ok";
    },
    checkWeixin:function(weixin){
        if(weixin==''|| $.trim(weixin).length<1){
            return "请您输入的微信号 ";
        }
        //^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/
        if(!weixin.match(/^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/)){
            $("input[name='wxmpNum']").val("");
            $("input[name='wxmpNum']").focus();
            return "您输入的微信号格式有误，请重新输入！";
        }
        return "ok";
    },
    checkDescript:function(reqDesc){
        if(!reqDesc.match(/^(?!_)(?!.*?_$)[,，\。\-.、；;\：:\"“\'！!\%+?？\s_a-zA-Z0-9_\u4e00-\u9fa5]{1,300}$/)){
            $("input[name='descript']").val("");
            $("input[name='descript']").focus();
            return "您最多可以输入300字，请精简后再次提交!";
        }
        return "ok";
    },
    //输入金额正则校验  -- ^((/^([1-9]\d*|0)(\.\d{1,2})?$/))$ 正整数、小数点后两位
    checkMoney:function(money){
        if(money==''|| $.trim(money).length==0){
            return "请输入微信公众号价格 ";
        }
        if(!money.match(/^([1-9]\d*|0)(\.\d{1,2})?$/)){
            $("input[name='wprice']").val("");
            $("input[name='wprice']").focus();
            return "您输入的价格有误，请重新输入！";
        }
        return "ok";
    }
}