package demo.shiro;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5 {
    /*
     * base64加密
     * */
    public static String encodeBase64(String str) {
        return Base64.encodeToString(str.getBytes());
    }

    /*
     * base64解密
     * */

    public static String decodeBase64(String str) {
        return Base64.decodeToString(str);
    }

    /*
     * Md5加密，shiro框架中自带Md5，Md5没有解密
     * */
    public static String md5(String str, String salt) {
        return new Md5Hash(str, salt).toString();
    }

    public static void main(String[] args) {
        String password = "123456";
        String username = "admin";
        String salt = username + "md5!@#";

        SimpleHash simpleHash = new SimpleHash("md5", password, ByteSource.Util.bytes(salt), 2);

        String passwordMd5 = simpleHash.toString();
        System.out.println("Md5加密:" + passwordMd5 + "   " + passwordMd5.length());
    }
}