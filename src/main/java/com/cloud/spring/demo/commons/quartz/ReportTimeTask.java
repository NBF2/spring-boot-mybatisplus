package com.cloud.spring.demo.commons.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
@Slf4j
@Component
public class ReportTimeTask implements QuartzScheduleTask {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exec() {
        log.info("执行的Quartz指定的任务，现在的时间是：{}", dateFormat.format(new Date()));
    }
}
