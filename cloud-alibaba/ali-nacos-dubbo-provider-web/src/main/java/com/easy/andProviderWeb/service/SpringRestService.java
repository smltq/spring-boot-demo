package com.easy.andProviderWeb.service;

import com.easy.and.api.service.RestService;
import com.easy.and.api.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Service(version = "1.0.0")
@RestController
@Slf4j
public class SpringRestService implements RestService {

    @Override
    @GetMapping(value = "/param")
    public String param(@RequestParam String param) {
        log.info("/param", param);
        return param;
    }

    @Override
    @PostMapping("/params")
    public String params(@RequestParam int a, @RequestParam String b) {
        log.info("/params", a + b);
        return a + b;
    }

    @Override
    @GetMapping("/headers")
    public String headers(@RequestHeader("h") String header,
                          @RequestHeader("h2") String header2, @RequestParam("v") Integer param) {
        String result = header + " , " + header2 + " , " + param;
        log.info("/headers", result);
        return result;
    }

    @Override
    @GetMapping("/path-variables/{p1}/{p2}")
    public String pathVariables(@PathVariable("p1") String path1,
                                @PathVariable("p2") String path2, @RequestParam("v") String param) {
        String result = path1 + " , " + path2 + " , " + param;
        log.info("/path-variables", result);
        return result;
    }

    @Override
    @PostMapping("/form")
    public String form(@RequestParam("f") String form) {
        return String.valueOf(form);
    }

    @Override
    @PostMapping(value = "/request/body/map", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public User requestBodyMap(@RequestBody Map<String, Object> data,
                               @RequestParam("param") String param) {
        User user = new User();
        user.setId(((Integer) data.get("id")).longValue());
        user.setName((String) data.get("name"));
        user.setAge((Integer) data.get("age"));
        log.info("/request/body/map", param);
        return user;
    }

    @PostMapping(value = "/request/body/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Override
    public Map<String, Object> requestBodyUser(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("age", user.getAge());
        return map;
    }
}
