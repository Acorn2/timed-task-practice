package com.msdn.time;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * java.util.Timer 实现定时任务.
 *
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/22 5:44 下午
 */
public class TimerUse {

  public static void main(String[] args) {
    System.out.println("当前时间: " + new Date() + "n" +
        "线程名称: " + Thread.currentThread().getName());

    testTimer1();
//    testTimer2();
//    testTimer3();
//    testTimer4();
  }

  // 方法一：设定指定任务task在指定时间time执行 schedule(TimerTask task, long delay)
  public static void testTimer1() {
    Timer timer = new Timer(true);
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("当前时间: " + new Date() + "n" +
            "线程名称: " + Thread.currentThread().getName());
      }
    }, 3500);
    // 设定指定的时间time为3500毫秒
  }

  /**
   * 方法二：设定指定任务task在指定延迟delay后间隔指定时间peroid执行 schedule(TimerTask task, long delay, long period)
   */
  public static void testTimer2() {
    Timer timer = new Timer("Timer");
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("当前时间: " + new Date() + "n" +
            "线程名称: " + Thread.currentThread().getName());
      }
    }, 2000, 3500);
  }


  /**
   * 方法三：在指定的时间执行任务 schedule(TimerTask task, Date time)
   */

  public static void testTimer3() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, 1); // 往后推一分钟

    Date time = calendar.getTime();    //获取当前系统时间

    Timer timer = new Timer("Timer");
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("当前时间: " + new Date() + "n" +
            "线程名称: " + Thread.currentThread().getName());
      }
    }, time);
  }

  /**
   * 方法四：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行． schedule(TimerTask task, Date firstTime,
   * long period)
   */
  public static void testTimer4() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制小时
    calendar.set(Calendar.MINUTE, 0);    // 控制分钟
    calendar.set(Calendar.SECOND, 0);    // 控制秒

    Date time = calendar.getTime();    //获取当前系统时间

    Timer timer = new Timer("Timer");
    timer.schedule(new TimerTask() {
      public void run() {
        System.out.println("当前时间: " + new Date() + "n" +
            "线程名称: " + Thread.currentThread().getName());
      }
    }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
  }
}
