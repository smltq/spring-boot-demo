package com.license.client;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class IpConfig {
    public static void main(String[] args) throws Exception {
        InetAddress ia = InetAddress.getLocalHost();
        try {
            String hostName = ia.getHostName();
            String ip = ia.getHostAddress();
            System.out.println("本机名称是：" + hostName);
            System.out.println("本机的ip是 ：" + ip);
            System.out.println("1本机的MAC是 ：" + getMACAddress1(ia));
            System.out.println("2本机的MAC是 ：" + getMACAddress2(ia));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取MAC地址的方法1
    private static String getMACAddress1(InetAddress ia) throws Exception {
        byte[] hardwareAddress = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        String[] hexadecimal = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) {
            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
        }
        return String.join("-", hexadecimal);
    }

    // 获取MAC地址的方法2
    private static String getMACAddress2(InetAddress ia) throws Exception {
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }
}
