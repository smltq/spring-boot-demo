package demo.quartz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
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
