package demo.shiro.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@Slf4j
public class AccountController {

    /**
     * 登录
     *
     * @param username
     * @param password
     * @param map      如果出错，回传给前端的map
     * @return
     */
    @RequestMapping("/login")
    public String login(String username, String password, Map<String, Object> map) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        String msg = "";
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException e) {
            log.info("IncorrectCredentialsException -- > 密码不正确");
            msg = "IncorrectCredentialsException -- > 密码不正确";
        } catch (UnknownAccountException e) {
            log.info("UnknownAccountException -- > 账号不存在");
            msg = "UnknownAccountException -- > 账号不存在";
        } catch (Exception e) {
            log.info("异常： -- >" + e);
            msg = "异常： " + e;
        }

        //判断登录是否出现错误
        if (msg.length() > 0) {
            map.put("msg", msg);
            return "/login";
        } else {
            return "redirect:index";
        }
    }

    @RequestMapping("/403")
    public String unauthorized() {
        log.info("------没有权限-------");
        return "403";
    }
}