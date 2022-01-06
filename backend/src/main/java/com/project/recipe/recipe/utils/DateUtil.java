package com.project.recipe.recipe.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    public static String createFormattedStringDate(Date dateToConvert) {
        return DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_dd_MM_yyyy).format(new Timestamp(
                dateToConvert.getTime()).toLocalDateTime());
    }

    public static Date createFormattedDate(String creationDate) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT_dd_MM_yyyy);
        return Date.from(LocalDateTime.parse(creationDate,dtf).atZone(ZoneId.systemDefault()).toInstant());

    }
}
