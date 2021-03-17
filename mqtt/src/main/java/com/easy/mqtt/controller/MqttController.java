package com.easy.mqtt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    @RequestMapping("/")
    public String index() {
        return "Hello MQTT!";
    }
}