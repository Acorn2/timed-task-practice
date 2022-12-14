package com.msdn.time.service;

import com.msdn.time.model.XxlJobInfo;
import com.msdn.time.util.DateUtils;
import com.msdn.time.util.XxlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/26 7:47 下午
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class XxlService {

  private final XxlUtil xxlUtil;

  @Value("${xxl.job.executor.appname}")
  private String appName;

  public void addJob(XxlJobInfo xxlJobInfo) {
    xxlUtil.addJob(xxlJobInfo, appName);
    long triggerNextTime = xxlJobInfo.getTriggerNextTime();
    log.info("任务已添加，将在{}开始执行任务", DateUtils.formatDate(triggerNextTime));
  }

}
