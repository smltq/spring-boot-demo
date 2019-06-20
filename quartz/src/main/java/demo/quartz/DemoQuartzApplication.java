package demo.quartz;

import org.quartz.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoQuartzApplication.class, args);
    }

    @Bean
    public JobDetail demoJobDetail() {
        return JobBuilder.newJob(DemoJob.class).withIdentity("demoJob").usingJobData("name", "World").storeDurably().build();
    }

    @Bean
    public Trigger demoJobTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(2).repeatForever();
        return TriggerBuilder.newTrigger().forJob(demoJobDetail()).withIdentity("demoTrigger").withSchedule(scheduleBuilder).build();
    }
}
