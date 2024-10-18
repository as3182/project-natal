package com.goodstransport.driver.logging;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization if needed
    }

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Log the HTTP request details
        logger.info("Request Method: {}, Request URI: {}", req.getMethod(), req.getRequestURI());
        logger.info("Request Headers: {}", req.getHeaderNames());

        // Log other useful request data like parameters or body if needed
        chain.doFilter(request, response);

        // Log the HTTP response details if needed
        logger.info("Response Status: {}", res.getStatus());
    }

    @Override
    public void destroy() {
        // Cleanup if needed
    }
}
