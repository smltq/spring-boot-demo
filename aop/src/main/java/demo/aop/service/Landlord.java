package demo.aop.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// 房东只要关心自己的核心业务功能
public class Landlord {
    @Value("${landlord:某某}")
    private String landlord;

    public void service() {
        System.out.println(landlord + "负责签合同");
        System.out.println(landlord + "负责收房租");
    }
}