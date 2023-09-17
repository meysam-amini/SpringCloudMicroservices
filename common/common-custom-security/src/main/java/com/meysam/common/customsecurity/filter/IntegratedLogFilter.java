package com.meysam.common.customsecurity.filter;


import com.meysam.common.customsecurity.utils.CustomSecurityUtils;
import com.meysam.common.model.other.LogContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class IntegratedLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String randomLogNumber = UUID.randomUUID().toString();
        try {
            LogContextHolder.save(randomLogNumber);
            log.info("request to: {} started for client: {} with log tracking number: {}", request.getRequestURI(), CustomSecurityUtils.getCurrentClient().orElse(null), randomLogNumber);
            filterChain.doFilter(request, response);
            log.info("request  with unique identifier : {} finished successfully", LogContextHolder.read());
        } catch (Exception exception) {
            log.info("request  with unique identifier : {} encounter exception, the exception is : {} ", LogContextHolder.read(), exception);
            throw exception;
        } finally {
            LogContextHolder.remove();
        }
    }
}
