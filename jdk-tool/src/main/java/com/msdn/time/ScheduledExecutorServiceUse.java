package com.msdn.time;

import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 9:02 上午
 */
public class ScheduledExecutorServiceUse {

  public static void main(String[] args) throws InterruptedException {
    TimerTask repeatedTask = new TimerTask() {
      @SneakyThrows
      public void run() {
        System.out.println("当前时间: " + new Date() + "n" +
            "线程名称: " + Thread.currentThread().getName());
      }
    };
    System.out.println("当前时间: " + new Date() + "n" +
        "线程名称: " + Thread.currentThread().getName());
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    long delay = 1L;
    long period = 2L;
    // 延迟1s，周期2s
    executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.SECONDS);
    // 定时任务重复执行3个周期
    Thread.sleep((delay + period * 3) * 1000);
    executor.shutdown();
  }

}
