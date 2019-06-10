package demo.aop;

import demo.aop.service.Landlord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoAopApplication implements CommandLineRunner {

    @Autowired
    private Landlord landlord;

    @Override
    public void run(String... args) {
        landlord.service();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoAopApplication.class, args);
    }
}
