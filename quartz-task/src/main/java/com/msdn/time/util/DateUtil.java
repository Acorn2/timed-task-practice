package com.msdn.time.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author hresh
 * @博客 https://juejin.cn/user/2664871918047063
 * @网站 https://www.hreshhao.com/
 * @date 2022/11/23 5:22 下午
 */
public class DateUtil {

  public static final String DATE_FORMAT_WITHOUT_HYPHEN = "yyyyMMdd";
  private static final ZoneId ZONE_ID = TimeZone.getDefault().toZoneId();

  public static long toEpochMilli(LocalDateTime localDateTime) {
    if (null == localDateTime) {
      return 0L;
    }
    return localDateTime.atZone(ZONE_ID).toInstant().toEpochMilli();
  }

  public static String getDay(LocalDateTime dateTime) {
    long localDateTime = toEpochMilli(dateTime);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_WITHOUT_HYPHEN);
    return simpleDateFormat.format(localDateTime);
  }

  public static String formatDateByPattern(Date date,String dateFormat){
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
    String formatTimeStr = null;
    if (date != null) {
      formatTimeStr = sdf.format(date);
    }
    return formatTimeStr;
  }

  public static String getCron(Date  date){
    String dateFormat="ss mm HH dd MM ? yyyy";
    return formatDateByPattern(date,dateFormat);
  }
}
