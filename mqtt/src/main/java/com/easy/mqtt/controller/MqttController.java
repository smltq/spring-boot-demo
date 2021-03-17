package com.easy.mqtt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * MQTT消息发送
 */
@RestController
public class MqttController {

    /**
     * 注入发送MQTT的Bean
     */
    @Resource
    private IMqttSender iMqttSender;

    /**
     * 发送MQTT消息
     *
     * @param message 消息内容
     * @return 返回
     */
    @ResponseBody
    @GetMapping(value = "/mqtt", produces = "text/html")
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "msg") String message) {
        iMqttSender.sendToMqtt(message);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}