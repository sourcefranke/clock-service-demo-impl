package com.github.sourcefranke.clock_service_demo_impl.time;

import com.github.sourcefranke.clock_service_demo_impl.api.TimeApiDelegate;
import com.github.sourcefranke.clock_service_demo_impl.model.GetTime200Response;
import com.github.sourcefranke.clock_service_demo_impl.time.model.Time;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class TimeApiDelegateImpl implements TimeApiDelegate {

    private final TimeMapper mapper;

    @Autowired
    public TimeApiDelegateImpl(TimeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<GetTime200Response> getTime(String dateFormat, String timeZone) {
        var time = Utils.currentTime(dateFormat, timeZone);
        var response = mapper.map(time);
        log.info("GET /time?date-format={}&time-zone={}, Response: {}", dateFormat, timeZone, response);
        return ResponseEntity.ok(response);
    }
}
