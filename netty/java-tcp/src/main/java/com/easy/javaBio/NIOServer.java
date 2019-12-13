package com.easy.javaBio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

@Slf4j
public class NIOServer {
    private InetAddress addr;
    private int port;
    private Selector selector;
    private Map<SocketChannel, List<byte[]>> dataMap;
    private static int BUFF_SIZE = 8192;

    public NIOServer(InetAddress addr, int port) throws IOException {
        this.addr = addr;
        this.port = port;
        dataMap = new HashMap<>();
        startServer();
    }

    private void startServer() throws IOException {
        // 获得selector及通道(socketChannel)
        this.selector = Selector.open();
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        // 绑定地址及端口
        InetSocketAddress listenAddr = new InetSocketAddress(this.addr, this.port);
        serverChannel.socket().bind(listenAddr);
        serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        log.info("NIOServer运行中...按下Ctrl-C停止服务");

        while (true) {
            log.info("服务器等待新的连接和selector选择…");
            this.selector.select();

            // 选择key工作
            Iterator keys = this.selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = (SelectionKey) keys.next();

                // 防止出现重复的key，处理完需及时移除
                keys.remove();

                //无效直接跳过
                if (!key.isValid()) {
                    continue;
                }
                if (key.isAcceptable()) {
                    this.accept(key);
                } else if (key.isReadable()) {
                    this.read(key);
                } else if (key.isWritable()) {
                    this.write(key);
                } else if (key.isConnectable()) {
                    this.doConnect(key);
                }
            }
        }
    }

    private void doConnect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        if (channel.finishConnect()) {
            // 成功
            log.info("成功连接了");
        } else {
            // 失败
            log.info("失败连接");
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        channel.write(ByteBuffer.wrap("欢迎，这是NIO服务\r\n".getBytes()));

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        log.info("连接到: " + remoteAddr);
        dataMap.put(channel, new ArrayList<>()); // 注册通道
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(BUFF_SIZE);
        int numRead = -1;
        try {
            numRead = channel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (numRead == -1) {
            this.dataMap.remove(channel);
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            log.info("关闭客户端连接: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        log.info("得到了: " + new String(data));

        doReplyClient(key, data); // 回复客户端
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = this.dataMap.get(channel);
        Iterator<byte[]> items = pendingData.iterator();

        String reMsg = "这是BIOServer给你的回复消息" + System.currentTimeMillis();
        while (items.hasNext()) {
            byte[] item = items.next();
            items.remove();
            //channel.write(ByteBuffer.wrap(reMsg.getBytes()));
            channel.write(ByteBuffer.wrap(item));
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    //回复客户端
    private void doReplyClient(SelectionKey key, byte[] data) {
        SocketChannel channel = (SocketChannel) key.channel();
        List<byte[]> pendingData = this.dataMap.get(channel);
//        String reMsg = "这是BIOServer给你的回复消息" + System.currentTimeMillis();
//        pendingData.add(reMsg.getBytes());
        pendingData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    public static void main(String[] args) throws IOException {
        new NIOServer(null, 10002);
    }
}
