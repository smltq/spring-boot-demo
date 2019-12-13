package com.easy.javaBio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class BIOClient {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("0.0.0.0", 10002);

        InputStream input = s.getInputStream();
        OutputStream output = s.getOutputStream();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(output));
        bw.write("客户端给服务端发消息测试\n");  //向服务器端发送一条消息
        bw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(input));  //读取服务器返回的消息
        String mess = br.readLine();
        log.info("服务器：" + mess);
    }
}