package com.easy.javaBio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

@Slf4j
public class NIOClient {
    private static int BUFF_SIZE = 1024;

    public static void main(String[] args) throws IOException, InterruptedException {

        InetSocketAddress socketAddress = new InetSocketAddress("0.0.0.0", 10002);
        SocketChannel socketChannel = SocketChannel.open(socketAddress);

        log.info("连接 BIOServer 服务，端口：10002...");

        ArrayList<String> companyDetails = new ArrayList<String>();

        // 创建消息列表
        companyDetails.add("腾讯");
        companyDetails.add("阿里巴巴");
        companyDetails.add("京东");
        companyDetails.add("百度");
        companyDetails.add("google");

        for (String companyName : companyDetails) {
            byte[] message = companyName.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            socketChannel.write(buffer);

            log.info("发送: " + companyName);
            buffer.clear();

            ByteBuffer redBuffer = ByteBuffer.allocate(BUFF_SIZE);
            socketChannel.read(redBuffer);
            String result = new String(buffer.array()).trim();
            log.info("接收到了来自服务端的消息：" + result);

            // 等待2秒钟再发送下一条消息
            Thread.sleep(2000);
        }

        socketChannel.close();
    }
}
