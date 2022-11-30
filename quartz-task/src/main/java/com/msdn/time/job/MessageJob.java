package com.msdn.time.job;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.context.ApplicationContext;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 3:10 下午
 */
@Setter
@Slf4j
public class MessageJob implements Job {

  private ApplicationContext applicationContext;

  private String jobData;

  private String jobHandlerClass;

  @SneakyThrows
  @Override
  public void execute(JobExecutionContext context) {
    log.info("quartz job data: " + jobData + ", jobHandlerClass: " + jobHandlerClass);
    MessageHandler messageHandler = (MessageHandler) applicationContext.getBean(jobHandlerClass);
    messageHandler.handlerMessage(jobData);
  }
}
