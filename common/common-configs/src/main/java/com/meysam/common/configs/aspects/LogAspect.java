package com.meysam.common.configs.aspects;

import com.meysam.common.utils.logs.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LogAspect {


    @Around(value = "@annotation(com.meysam.common.configs.annotations.MethodLog)")
    public Object loggerPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        long trackingCode = System.identityHashCode(joinPoint.getArgs());
        String logIdentifier = LogUtils.getLogIdentifier() != null ? LogUtils.getLogIdentifier() : String.valueOf(trackingCode);
        String methodName = joinPoint.getSignature().getName();
        Class<?> clazz = joinPoint.getTarget().getClass();
        if (Repository.class.isAssignableFrom(clazz)) {
            clazz = Arrays.stream(joinPoint.getTarget().getClass().getInterfaces()).findFirst().orElse(clazz);
        }
        String className = clazz.getName();

        log.info("calling '{}' method on '{}' class with trackingCode: '{}' is starting with following arguments: {}",
                methodName, className, logIdentifier, joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();
            log.info("calling '{}' method on '{}' class with trackingCode: '{}' is done", methodName, className, logIdentifier);
            return result;
        } catch (Exception exception) {
            log.info("calling '{}' method on '{}' class with trackingCode: '{}' encounter exception, the exception is : {}", methodName, className, logIdentifier, exception);
            throw exception;
        }
    }
}
