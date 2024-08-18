package com.github.sourcefranke.clock_service_demo_impl;

import com.github.sourcefranke.clock_service_demo_impl.api.TimeApiDelegate;
import com.github.sourcefranke.clock_service_demo_impl.model.GetTime200Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TimeApiDelegateImpl implements TimeApiDelegate {

    @Override
    public ResponseEntity<GetTime200Response> getTime(String dateFormat, String timeZone) {
        log.debug("getTime, parameters: dateFormat [{}], timeZone [{}]", dateFormat, timeZone);
        var response = new GetTime200Response()
                .time(LocalDateTime.now(ZoneId.of(timeZone)).format(DateTimeFormatter.ofPattern(dateFormat)))
                .dateFormat(dateFormat)
                .timeZone(timeZone);
        log.info("GET /time?date-format={}&time-zone={}, Response: {}", dateFormat, timeZone, response);
        return ResponseEntity.ok(response);
    }
}
