package com.msdn.time.service;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 10:09 上午
 */
@Service
@Slf4j
public class ScheduledTaskService {

  private final AtomicInteger counts = new AtomicInteger();

  //  @Scheduled(cron = "0 0/10 * ? * ?")//每10分钟执行一次
  @Scheduled(fixedRate = 3000) // 每 3秒执行一次
  public void pushMessage() {
    log.info("[execute]定时第({})给用户发送通知", counts.incrementAndGet());
  }

}
