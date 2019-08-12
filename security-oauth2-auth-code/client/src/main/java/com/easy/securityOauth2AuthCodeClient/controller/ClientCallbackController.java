package com.easy.securityOauth2AuthCodeClient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientCallbackController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/aiqiyi/qq/redirect")
    public String getToken(@RequestParam String code){
        log.info("receive code {}",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","aiqiyi");
        params.add("client_secret","secret");
        params.add("redirect_uri","http://localhost:8081/aiqiyi/qq/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
        String token = response.getBody();
        log.info("token => {}",token);
        return token;
    }

}
