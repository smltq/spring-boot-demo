package com.easy.securityOauth2AuthCodeServer.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/account")
public class RestfulApiProviderController {

    @RequestMapping("/info/{account}")
    public Account info(@PathVariable("account") String account) {
        return InMemoryDatabase.database.get(account);
    }

    @RequestMapping("child/{account}")
    public List<Account> child(@PathVariable("account") String qq) {
        return InMemoryDatabase.database.get(qq).getChildAccount();
    }
}