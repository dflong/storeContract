package com.dflong.storecontract.manage;

import com.dflong.storeapi.api.ThrowException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    static final DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");

    static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyyMMddHH");

    public static int dayDiffer(Date start, Date end) {
        if (start.after(end)) return dayDiffer(end, start);
        if (end == null) {
            throw new ThrowException(-1, "日期不能为空");
        }
        LocalDateTime startDate = start.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime endDate = end.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        int dayDiffer = (int) (endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay());
        if (startDate.plusDays(dayDiffer).isBefore(endDate)) {
            dayDiffer ++;
        }
        return dayDiffer;
    }

    /**
     * 转换为yyyyMMdd格式的整数
     * @return 如：20260412
     */
    public static int dateToInt(Date date) {
        if (date == null) {
            throw new ThrowException(-1, "日期不能为空");
        }
        LocalDateTime localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return Integer.parseInt(localDate.format(formatter2));
    }

    /**
     * 转换为yyyyMMddHH格式的整数
     * @return 如：2026041215
     */
    public static int dateToInt2(Date date) {
        if (date == null) {
            throw new ThrowException(-1, "日期不能为空");
        }
        LocalDateTime localDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return Integer.parseInt(localDate.format(formatter3));
    }

    /**
     * yyyyMMddHH 2026041215 转换为 Date
     */
    public static Date intToDate(int day) {
        LocalDateTime localDateTime = LocalDateTime.parse(String.valueOf(day), formatter2);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault())
                .toInstant());
    }

    public static Date fromLocalDateTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault())
                .toInstant());
    }

     public static LocalDateTime toLocalDateTime(Date date) {
         return date.toInstant()
                 .atZone(ZoneId.systemDefault())
                 .toLocalDateTime();
     }

    public static Date fromString(String dateStr) {
        return Date.from(LocalDateTime.parse(dateStr, formatter1).atZone(ZoneId.systemDefault()).toInstant());
    }
}
