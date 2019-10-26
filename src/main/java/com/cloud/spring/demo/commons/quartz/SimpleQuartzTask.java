package com.cloud.spring.demo.commons.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Description:
 * @author: Administrator
 * @date: 2019/10/26
 * @modified by:
 * @modified date:
 * @problem no:
 */
@Slf4j
public class SimpleQuartzTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("简单的单Quartz任务开始---");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            log.error("线程阻塞失败", e);
        }

        log.info("简单的单Quartz任务结束---");
    }
}
