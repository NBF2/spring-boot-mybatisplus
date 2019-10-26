package com.cloud.spring.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @author: Administrator
 * @date: 2019/10/26
 * @modified by:
 * @modified date:
 * @problem no:
 */
@Configuration
@EnableScheduling
@Slf4j
public class SpringScheduleConfig {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //@Scheduled(fixedRate = 1000 * 60L)
    public void runIntervalTask() {
        log.info("Spring3.*之后的自带定时任务实现 ===> fixedRate表示按固定频率执行该任务---打印当前时间：{}", dateFormat.format(new Date()));
    }

    //@Scheduled(cron = "47 * * * * ?")
    public void runCornExpTask() {
        log.info("Spring3.*之后的自带定时任务实现 ===> cron表示按cron表达式执行该任务---打印当前时间：{}", dateFormat.format(new Date()));
    }
}
