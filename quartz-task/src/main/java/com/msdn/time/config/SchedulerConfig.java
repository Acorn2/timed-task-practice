package com.msdn.time.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 10:45 上午
 */
@Configuration
public class SchedulerConfig {

  @Bean
  public SchedulerFactoryBean scheduler(DataSource dataSource) {
    SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    schedulerFactory.setConfigLocation(new ClassPathResource("quartz.properties"));
    schedulerFactory.setDataSource(dataSource);
    schedulerFactory.setJobFactory(new SpringBeanJobFactory());
    schedulerFactory.setApplicationContextSchedulerContextKey("applicationContext");
    return schedulerFactory;
  }

}
