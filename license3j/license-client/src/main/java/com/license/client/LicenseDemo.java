package com.license.client;

import javax0.license3j.HardwareBinder;

public class LicenseDemo {
    public static void main(String[] args) throws Exception {
        HardwareBinder hb = new HardwareBinder();
        System.out.printf("machine id：%s\n", hb.getMachineIdString());
    }
}
