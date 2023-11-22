package com.ead.authuser.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtils {
    public String parseDate(LocalDateTime data) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(data);
    }
}
