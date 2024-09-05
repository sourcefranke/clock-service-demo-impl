package com.github.sourcefranke.clock_service_demo_impl.time;

import com.github.sourcefranke.clock_service_demo_impl.model.GetTime200Response;
import com.github.sourcefranke.clock_service_demo_impl.time.model.Time;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TimeMapper {

    @Mapping(source = "value", target = "time")
    GetTime200Response map(Time time);
}
