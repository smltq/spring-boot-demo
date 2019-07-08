package demo.pac4j.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {
    @RequestMapping({"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequiresPermissions(value = "role:edit")
    @GetMapping(value = "/roles/{id}")
    public String put() {
        return "允许修改角色";
    }

    @RequiresPermissions(value = "user:info")
    @GetMapping(value = "/users/{id}")
    public PrincipalCollection getUserById() {
        return SecurityUtils.getSubject().getPrincipals();
    }

    @GetMapping(value = "/you")
    public String you() {
        return "you you 不需要权限";
    }
}