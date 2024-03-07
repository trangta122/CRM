package com.intern.crm.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserModelMapper {
    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
