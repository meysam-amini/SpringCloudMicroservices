package com.meysam.backoffice.webapi.config.aspect.log;


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


    @Around(value = "@annotation(com.meysam.backoffice.webapi.config.aspect.log.annotation.MethodLog)")
    public Object loggerPointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        long trackingCode = System.identityHashCode(joinPoint.getArgs());
        String methodName = joinPoint.getSignature().getName();
        Class<?> clazz = joinPoint.getTarget().getClass();
        if (Repository.class.isAssignableFrom(clazz)) {
            clazz = Arrays.stream(joinPoint.getTarget().getClass().getInterfaces()).findFirst().orElse(clazz);
        }
        String className = clazz.getName();

        log.info("calling '{}' method on '{}' class  is starting with following arguments: {}",
                methodName, className, serializeArguments(/*serializer,*/ joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            log.info("calling '{}' method on '{}' class is done", methodName, className);
            return result;
        } catch (Exception exception) {
            log.info("calling '{}' method on '{}' class encounter exception, the exception is : {}", methodName, className, exception);
            throw exception;
        }
    }

    public static String serializeArguments(Object[] args) {
        try {
            return Arrays.toString(args);
        } catch (Throwable exception) {
            log.error("ERROR in serializing arguments", exception);
            return "$ERROR_SERIALIZING_ARGUMENTS$" + exception.getMessage();
        }
    }
}