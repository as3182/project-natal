package com.goodstransport.driver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingConfig {

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);       // Logs client info like remote address
        filter.setIncludeQueryString(true);      // Logs query parameters
        filter.setIncludePayload(true);          // Logs request body
        filter.setIncludeHeaders(false);         // You can set true to log headers
        filter.setMaxPayloadLength(10000);       // Controls the maximum size of payload to log
        return filter;
    }
}

