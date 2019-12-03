package com.easy.arConsumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySource {
    @Input("input1")
    SubscribableChannel input1();
}
