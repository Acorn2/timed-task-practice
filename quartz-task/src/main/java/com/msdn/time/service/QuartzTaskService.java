package com.msdn.time.service;

import cn.hutool.core.util.StrUtil;
import com.msdn.time.dto.JobResponse;
import com.msdn.time.dto.ScheduleTask;
import com.msdn.time.job.MessageJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/24 5:48 下午
 */
@Service
@RequiredArgsConstructor
public class QuartzTaskService {

  public static final String JOB_DATA_KEY = "jobData";
  public static final String JOB_HANDLER_CLASS_KEY = "jobHandlerClass";
  private final Scheduler scheduler;

  public void createJob(ScheduleTask task) throws SchedulerException {
    JobDetail jobDetail = JobBuilder.newJob().ofType(MessageJob.class)
        .withIdentity(task.getJobName(), task.getGroupName())
        .usingJobData(JOB_DATA_KEY, task.getJobData())
        .usingJobData(JOB_HANDLER_CLASS_KEY, task.getJobHandlerClass())
        .build();
    Trigger trigger;
    if (StrUtil.isNotBlank(task.getJobCronTime())) {
      trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
          .withIdentity(task.getJobName() + "_trigger", task.getGroupName())
          .withSchedule(CronScheduleBuilder.cronSchedule(task.getJobCronTime())).build();
    } else {
      trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
          .withIdentity(task.getJobName() + "_trigger", task.getGroupName())
          .startAt(new Date(task.getJobTime()))
          .build();
    }

    scheduler.scheduleJob(jobDetail, trigger);
  }


  // 修改 一个job的 时间表达式
  @SneakyThrows
  public void updateJob(String jobName, String jobGroupName, String jobTime) {
    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
    trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
        .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
    // 重启触发器
    scheduler.rescheduleJob(triggerKey, trigger);
  }

  @SneakyThrows
  public void removeTask(JobKey jobKey) {
    scheduler.deleteJob(jobKey);
  }

  // 暂停一个job
  @SneakyThrows
  public void pauseJob(JobKey jobKey) {
    scheduler.pauseJob(jobKey);
  }

  // 恢复一个job
  @SneakyThrows
  public void resumeJob(JobKey jobKey) {
    scheduler.resumeJob(jobKey);
  }

  // 立即执行一个job
  @SneakyThrows
  public void runJobNow(JobKey jobKey) {
    scheduler.triggerJob(jobKey);
  }

  // 获取所有计划中的任务列表
  public List<JobResponse> queryAllJob() throws SchedulerException {
    List<JobResponse> jobResponses = new ArrayList<>();
    GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
    Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
    for (JobKey jobKey : jobKeys) {
      List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
      for (Trigger trigger : triggers) {
        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
        JobResponse jobResponse = getJobResponse(jobKey, trigger, triggerState);
        jobResponses.add(jobResponse);
      }
    }
    return jobResponses;
  }

  private JobResponse getJobResponse(JobKey jobKey, Trigger trigger, TriggerState triggerState) {
    JobResponse jobResponse = JobResponse.builder().jobName(jobKey.getName())
        .groupName(jobKey.getGroup()).triggerKey(trigger.getKey().toString()).build();
    jobResponse.setJobStatus(triggerState.name());
    if (trigger instanceof CronTrigger) {
      CronTrigger cronTrigger = (CronTrigger) trigger;
      String cronExpression = cronTrigger.getCronExpression();
      jobResponse.setJobCronTime(cronExpression);
    }
    return jobResponse;
  }

  // 获取所有正在运行的job
  public List<JobResponse> queryRunJob() throws SchedulerException {
    List<JobResponse> jobResponses = new ArrayList<>();
    List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
    for (JobExecutionContext executingJob : executingJobs) {
      JobDetail jobDetail = executingJob.getJobDetail();
      JobKey jobKey = jobDetail.getKey();
      Trigger trigger = executingJob.getTrigger();
      Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
      JobResponse jobResponse = getJobResponse(jobKey, trigger, triggerState);
      jobResponses.add(jobResponse);
    }
    return jobResponses;
  }
}
