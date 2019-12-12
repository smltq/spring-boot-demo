package com.easy.nettyServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetSocketAddress;

@SpringBootApplication
public class NettyServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
        //启动服务端
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(new InetSocketAddress("127.0.0.1", 8090));
    }
}