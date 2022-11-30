package com.msdn.time.job;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.msdn.time.model.User;
import com.msdn.time.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/25 10:38 上午
 */
@Component
@RequiredArgsConstructor
public class WeatherNotificationHandler implements MessageHandler {

  private final UserService userService;

  @Override
  public void handlerMessage(String jobData) throws JsonProcessingException {
    List<User> users = JSON.parseArray(jobData, User.class);
    userService.pushWeatherNotification(users);
  }
}
