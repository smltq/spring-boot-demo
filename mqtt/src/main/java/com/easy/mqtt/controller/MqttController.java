package com.easy.mqtt.controller;

import com.easy.mqtt.config.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * MQTT消息发送
 */
@RestController
@Slf4j
public class MqttController {
    /**
     * 注入发送MQTT的Bean
     */
    @Resource
    private MqttGateway mqttGateway;

    @Autowired
    private MqttConfig mqttConfig;

    /**
     * 发送MQTT消息
     *
     * @param msg 消息内容
     * @return 返回
     */
    @ResponseBody
    @PostMapping(value = "/sendMqtt", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "msg") String msg) {
        log.info("================生产MQTT消息===={}============", msg);
        mqttGateway.sendToMqtt(msg);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    /**
     * 发送MQTT消息
     *
     * @param msg 消息内容
     * @return 返回
     */
    @ResponseBody
    @PostMapping(value = "/sendMqtt2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMqtt2(@RequestParam(value = "msg") String msg) {
        log.info("================生产MQTT2消息===={}============", msg);
        mqttGateway.sendToMqtt("hello", msg);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "/consumer")
    public ResponseEntity<String> consumer(@RequestParam(value = "msg") String msg) {
        log.info("================消费消息===={}============", msg);
        mqttConfig.handler();
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}