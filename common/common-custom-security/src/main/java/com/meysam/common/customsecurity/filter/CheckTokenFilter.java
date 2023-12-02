package com.meysam.common.customsecurity.filter;

import com.meysam.common.customsecurity.TokenDecoder;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.CustomSecurityContext;
import com.meysam.common.customsecurity.model.OauthExtractedTokenDto;
import com.meysam.common.customsecurity.service.api.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/** This class should be used when we don't want to have
 * "spring-boot-starter-oauth2-resource-server" & "spring-boot-starter-oauth2-client"
 * dependencies and we want to have custom filter for calling /check-token path of
 * our authorization server.
 * */

@Slf4j
public class CheckTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher[] ignoredPathMatchers;
    private SecurityService securityService;

    public CheckTokenFilter(SecurityService securityService, String... ignoredPaths) {
        this.securityService = securityService;
        if (Objects.isNull(ignoredPaths)) {
            ignoredPathMatchers = new RequestMatcher[0];
            return;
        }
        ignoredPathMatchers = new RequestMatcher[ignoredPaths.length];
        for (int counter = 0; counter < ignoredPaths.length; counter++) {
            ignoredPathMatchers[counter] = new AntPathRequestMatcher(ignoredPaths[counter]);
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (Objects.isNull(authorizationHeader)) {
            log.info("calling service without any token from : {} ", request.getRemoteAddr());
            unauthenticated(response);
            return;
        }
        String authorizationToken = authorizationHeader.substring(7);
        if (securityService.checkToken(authorizationToken)) {
            SecurityPrinciple clientPrinciple = decodeToken(authorizationToken);
            SecurityContext securityContext = new CustomSecurityContext(clientPrinciple);
            try {
                SecurityContextHolder.setContext(securityContext);
                request.getSession().setAttribute(SessionConstants.CLIENT_SESSION, clientPrinciple);
                filterChain.doFilter(request, response);
            } finally {
                SecurityContextHolder.clearContext();
            }
        } else {
            log.info("someone try to call service with an invalid toke, from : {}", request.getRemoteAddr());
            unauthenticated(response);
            return;
        }
    }

    private void unauthenticated(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
    }

    private SecurityPrinciple decodeToken(String authorization) {
        SecurityPrinciple securityPrinciple = null;
        try {
            securityPrinciple = (SecurityPrinciple) TokenDecoder.decodeToken(authorization);
        } catch (Exception exception) {
            log.error("token is valid but decoding or casting token encounter exception, the exception is : {}", exception);
        }
        return securityPrinciple;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        for (RequestMatcher requestMatcher : ignoredPathMatchers) {
            if (requestMatcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
