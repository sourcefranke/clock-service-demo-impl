package com.github.sourcefranke.clock_service_demo_impl.time;

import com.github.sourcefranke.clock_service_demo_impl.time.model.Time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    private Utils() {}

    public static Time currentTime(String dateFormat, String timeZone) {
        var localDate = LocalDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(dateFormat));
        return new Time(localDate, dateFormat, timeZone);
    }
}
