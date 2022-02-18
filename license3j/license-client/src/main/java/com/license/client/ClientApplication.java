package com.license.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {
    public static void main(String[] args) {
        LicenseUtil license = LicenseUtil.getInstance();
        license.printLocalInfo();
        license.printLicenseInfo();
        if (license.isOk()) {
            license.isLocalMac();
            license.isLocalMachine();
            license.isUserLimit(5);
            SpringApplication.run(ClientApplication.class, args);
        } else {
            throw new RuntimeException("许可证书不可用！");
        }
    }
}
