package com.meysam.common.customsecurity.config;


import com.meysam.common.customsecurity.filter.CheckTokenFilter;
import com.meysam.common.customsecurity.filter.IntegratedLogFilter;
import com.meysam.common.customsecurity.model.constants.SecurityConstants;
import com.meysam.common.customsecurity.service.api.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.ConcurrentSessionFilter;


@ConditionalOnProperty(value = SecurityConstants.ENABLE_SECURITY, havingValue = "true")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SecurityService securityService;
    @Value(SecurityConstants.IGNORED_PATH_PROPERTY)
    private String[] ignoredPaths;

    public WebSecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable).anonymous().disable().csrf(AbstractHttpConfigurer::disable).formLogin().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(new CheckTokenFilter(securityService, ignoredPaths), ConcurrentSessionFilter.class)
                .addFilterAfter(new IntegratedLogFilter(), CheckTokenFilter.class);
        return http.build();
    }
}
