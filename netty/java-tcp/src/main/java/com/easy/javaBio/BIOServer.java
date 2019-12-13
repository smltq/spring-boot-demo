package com.easy.javaBio;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(10002);
        while (true) {
            Socket client = server.accept(); //等待客户端的连接，如果没有获取连接  ,在此步一直等待
            new Thread(new ServerThread(client)).start(); //为每个客户端连接开启一个线程
        }
        //server.close();
    }
}

@Slf4j
class ServerThread extends Thread {

    private Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void run() {
        log.info("客户端:" + client.getInetAddress().getLocalHost() + "已连接到服务器");
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //读取客户端发送来的消息
        String mess = br.readLine();
        log.info("客户端：" + mess);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        bw.write(mess + "\n");
        bw.flush();
    }
}