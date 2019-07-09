package demo.pac4j.web;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.pac4j.cas.client.rest.CasRestFormClient;
import org.pac4j.cas.profile.CasProfile;
import org.pac4j.cas.profile.CasRestProfile;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jwt.profile.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class IndexController {
    @Autowired
    private JwtGenerator generator;

    @Autowired
    private CasRestFormClient casRestFormClient;

    @Value("${cas.serviceUrl}")
    private String serviceUrl;

    @GetMapping("/")
    public Object index() {
        return "index page";
    }

    @GetMapping("/user/{id}")
    @RequiresRoles("aRoleName")
    public Object user(@PathVariable(value = "id") String id) {
        return "users page:" + id;
    }

    @GetMapping("/user/detail")
    public Object detail(HttpServletRequest request) {
        return "users:" + request.getUserPrincipal().getName();
    }

    @RequestMapping("login")
    public Object login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<>();
        J2EContext context = new J2EContext(request, response);
        final ProfileManager<CasRestProfile> manager = new ProfileManager(context);
        final Optional<CasRestProfile> profile = manager.get(true);
        //获取ticket
        TokenCredentials tokenCredentials = casRestFormClient.requestServiceTicket(serviceUrl, profile.get(), context);
        //根据ticket获取用户信息
        final CasProfile casProfile = casRestFormClient.validateServiceTicket(serviceUrl, tokenCredentials, context);
        //生成jwt token
        String token = generator.generate(casProfile);
        model.put("token", token);
        return new HttpEntity<>(model);
    }
}
