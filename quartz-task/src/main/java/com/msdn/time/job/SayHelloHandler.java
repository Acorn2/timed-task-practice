package com.msdn.time.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msdn.time.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 定时任务具体执行的业务逻辑.
 *
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 3:20 下午
 */
@Component
@RequiredArgsConstructor
public class SayHelloHandler implements MessageHandler {

  private final UserService userService;

  @Override
  public void handlerMessage(String jobData) throws JsonProcessingException {
    String username = new ObjectMapper().readValue(jobData, String.class);
    userService.sayHelloToUser(username);
  }

}
