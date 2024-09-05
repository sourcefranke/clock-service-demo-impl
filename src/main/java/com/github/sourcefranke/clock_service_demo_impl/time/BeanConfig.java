package com.github.sourcefranke.clock_service_demo_impl.time;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public TimeMapper timeMapper() {
        return Mappers.getMapper(TimeMapper.class);
    }
}
