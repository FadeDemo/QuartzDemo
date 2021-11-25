package org.fade.demo.quartzdemo.quartzspringboot;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @author fade
 * @date 2021/11/25
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return newJob(HelloSpringBoot.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail jobDetail) {
        return TriggerBuilder.newTrigger().startNow()
                .withSchedule(simpleSchedule()
                .withIntervalInSeconds(5)
                .withRepeatCount(2))
                .forJob(jobDetail)
                .build();
    }

}
