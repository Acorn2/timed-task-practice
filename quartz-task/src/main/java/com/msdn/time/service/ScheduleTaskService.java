package com.msdn.time.service;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdn.time.dto.ScheduleTask;
import com.msdn.time.mapper.UserMapper;
import com.msdn.time.model.User;
import com.msdn.time.util.DateUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/25 10:24 上午
 */
@Service
@RequiredArgsConstructor
public class ScheduleTaskService {

  private final QuartzTaskService quartzTaskService;
  private final UserMapper userMapper;

  @SneakyThrows
  public void createTask(String username) {
    LocalDateTime scheduleTime = LocalDateTime.now().plusMinutes(1L);
    String jobName = "sayHello";
    String jogGroupName = "group1";
    String jobHandlerClass = "sayHelloHandler";

    ScheduleTask scheduleTask = ScheduleTask.builder()
        .jobName(jobName)
        .groupName(jogGroupName)
        .jobData(new ObjectMapper().writeValueAsString(username))
        .jobHandlerClass(jobHandlerClass)
        .jobTime(DateUtil.toEpochMilli(scheduleTime))
        .build();

    quartzTaskService.createJob(scheduleTask);
  }

  @SneakyThrows
  public void createWeatherNotificationTask(String jobTime) {
    String jobName = "weatherNotification";
    String jogGroupName = "group2";
    String jobHandlerClass = "weatherNotificationHandler";

    List<User> users = userMapper.queryAll();

    ScheduleTask scheduleTask = ScheduleTask.builder()
        .jobName(jobName)
        .groupName(jogGroupName)
        .jobData(JSON.toJSONString(users))
        .jobHandlerClass(jobHandlerClass)
        .jobCronTime(jobTime)
        .build();

    quartzTaskService.createJob(scheduleTask);
  }

}
