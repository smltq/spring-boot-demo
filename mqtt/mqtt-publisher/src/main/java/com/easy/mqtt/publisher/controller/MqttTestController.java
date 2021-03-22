package com.easy.mqtt.publisher.controller;

import com.easy.mqtt.publisher.config.MqttGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * MQTT消息发送
 */
@RestController
@Slf4j
public class MqttTestController {
    /**
     * 注入发送MQTT的Bean
     */
    @Resource
    private MqttGateway mqttGateway;

    /**
     * 发送自定义消息内容（使用默认主题）
     *
     * @param msg 消息内容
     * @return 返回
     */
    @ResponseBody
    @PostMapping(value = "/sendMqtt", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMqtt(@RequestParam(value = "msg") String msg) {
        log.info("================生产默认主题的MQTT消息===={}============", msg);
        mqttGateway.sendToMqtt(msg);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }

    /**
     * 发送自定义消息内容，且指定主题
     *
     * @param msg 消息内容
     * @return 返回
     */
    @ResponseBody
    @PostMapping(value = "/sendMqtt2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendMqtt2(@RequestParam("topic") String topic, @RequestParam(value = "msg") String msg) {
        log.info("================生产自定义主题的MQTT消息===={}============", msg);
        mqttGateway.sendToMqtt(topic, msg);
        return new ResponseEntity<>("发送成功", HttpStatus.OK);
    }
}