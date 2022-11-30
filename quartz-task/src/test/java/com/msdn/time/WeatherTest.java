package com.msdn.time;

import com.msdn.time.dto.constant.WeatherConstant;
import com.msdn.time.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 3:34 下午
 */
@SpringBootTest(classes = QuartzApplication.class)
public class WeatherTest {

  @Autowired
  WeatherService weatherService;

  @Test
  void getWeather() {
    weatherService.getWeather(WeatherConstant.BEI_JING);
  }
}
