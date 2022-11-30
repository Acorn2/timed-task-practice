package com.msdn.time.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import com.msdn.time.common.exception.BusinessException;
import com.msdn.time.dto.UserRequest;
import com.msdn.time.dto.WeatherInfo;
import com.msdn.time.dto.constant.WeatherConstant;
import com.msdn.time.mapper.UserMapper;
import com.msdn.time.model.User;
import com.msdn.time.struct.UserStruct;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 3:12 下午
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

  private final ScheduleTaskService scheduleTaskService;
  private final UserMapper userMapper;
  private final UserStruct userStruct;
  private final WeatherService weatherService;

  /**
   * 假设有这样一个业务需求，每当有新用户注册，则1分钟后会给用户发送欢迎通知.
   *
   * @param userRequest 用户请求体
   */
  public void register(UserRequest userRequest) {
    if (Objects.isNull(userRequest) || isBlank(userRequest.getUsername()) ||
        isBlank(userRequest.getPassword())) {
      BusinessException.fail("账号或密码为空！");
    }

    User user = userStruct.toUser(userRequest);
    userMapper.insert(user);

    scheduleTaskService.createTask(user.getUsername());
  }


  public void sayHelloToUser(String username) {
    User user = userMapper.selectByUserName(username);
    String message = "Welcome to Java,I am hresh.";
    log.info(user.getUsername() + " , hello, " + message);
  }

  public void pushWeatherNotification(List<User> users) {
    log.info("执行发送天气通知给用户的任务。。。");
    WeatherInfo weatherInfo = weatherService.getWeather(WeatherConstant.WU_HAN);
    for (User user : users) {
      log.info(user.getUsername() + "----" + weatherInfo.toString());
    }
  }
}
