package com.meysam.common.customsecurity.filter;

import com.meysam.common.customsecurity.TokenDecoder;
import com.meysam.common.customsecurity.model.CustomSecurityContext;
import com.meysam.common.customsecurity.model.SecurityPrinciple;
import com.meysam.common.customsecurity.model.constants.SessionConstants;
import com.meysam.common.customsecurity.service.api.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

/**
 * This class should be used when we don't want to have
 * "spring-boot-starter-oauth2-resource-server" & "spring-boot-starter-oauth2-client"
 * dependencies and we want to have custom filter for calling /check-token path of
 * our authorization server, or decode jwt tokens and create principal on thread local.
 */

@Slf4j
public class CheckTokenFilter extends OncePerRequestFilter {

    private final RequestMatcher[] ignoredPathMatchers;
    private SecurityService securityService;
    private Boolean enableSecurity;

    public CheckTokenFilter(Boolean enableSecurity,SecurityService securityService, String... ignoredPaths) {
        this.securityService = securityService;
        this.enableSecurity=enableSecurity;
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
        if(enableSecurity) {
            if (Objects.isNull(authorizationHeader)) {
                log.info("calling service without any token from : {} ", request.getRemoteAddr());
                unauthenticated("Authorization header is missing ",response);
                return;
            }
            String authorizationToken = authorizationHeader.substring(7);
            if (securityService.checkToken(authorizationToken)) {
                try {
                    SecurityPrinciple clientPrinciple = decodeToken(authorizationToken);

                    if(!securityService.hasActiveSession(clientPrinciple.getUsername())){
                        unauthenticated("You have been logged out already",response);
                        return;
                    }
                    SecurityContext securityContext = new CustomSecurityContext(clientPrinciple);
                    SecurityContextHolder.setContext(securityContext);
                    request.getSession().setAttribute(SessionConstants.CLIENT_SESSION, clientPrinciple);
                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    log.error("token is valid or isTest is true, but decoding or casting token encounter exception at time:{}, the exception is : {}",System.currentTimeMillis(), e);
                    unauthenticated("Token is invalid ",response);
                }

                SecurityContextHolder.clearContext();
            }
            else {
                log.info("someone try to call service with an invalid toke, from : {}", request.getRemoteAddr());
                unauthenticated("Token is invalid ",response);
                return;
            }
        }else {
            filterChain.doFilter(request, response);
        }
    }

    @SneakyThrows
    private void unauthenticated(String msg,HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(msg);
    }

    private SecurityPrinciple decodeToken(String authorization) {
        SecurityPrinciple clientPrinciple = null;
        clientPrinciple = (SecurityPrinciple) TokenDecoder.decodeToken(authorization);
        return clientPrinciple;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        for (RequestMatcher requestMatcher : ignoredPathMatchers) {
            if (requestMatcher.matches(request)) {
//                    log.info("request:{}, matched to ignored path:{} ",request.getRequestURI().substring(request.getContextPath().length()),requestMatcher.toString());
                return true;
            }else {
//                    log.info("request:{}, not matched to ignored path:{} ",request.getRequestURI().substring(request.getContextPath().length()),requestMatcher.toString());
            }
        }
        return false;
    }
}