package com.cloud.spring.demo.commons;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: Administrator
 * @date: 2019/10/26
 * @modified by:
 * @modified date:
 * @problem no:
 */
@Configuration
public class SimpleQuartzConfig {
    @Bean
    public JobDetail simpleQuartzTaskDetail() {
        return JobBuilder.newJob(SimpleQuartzTask.class).withIdentity("simpleQuartzTask").storeDurably().build();
    }

    @Bean
    public Trigger simpleQuartzTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/3 * * * *  ?");
        return TriggerBuilder.newTrigger().forJob(simpleQuartzTaskDetail())
                .withIdentity("simpleQuartzTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
