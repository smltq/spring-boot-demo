package demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@EnableScheduling
public class ScheduledTaskTests {
    @Test
    public void test() {
        log.info("启动了ScheduledTask定时作业");
        while (true) {
        }
    }
}
