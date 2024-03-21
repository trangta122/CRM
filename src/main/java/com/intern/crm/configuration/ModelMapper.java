package com.intern.crm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {
    @Bean
    public org.modelmapper.ModelMapper modelMapperBean() {
        return new org.modelmapper.ModelMapper();
    }
}
