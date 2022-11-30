package com.msdn.time.job;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 3:10 下午
 */
public interface MessageHandler {

  void handlerMessage(String jobData) throws JsonProcessingException;

}
