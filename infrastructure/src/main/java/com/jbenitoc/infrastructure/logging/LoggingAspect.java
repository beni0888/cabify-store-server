package com.jbenitoc.infrastructure.logging;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    private static final String MDC_OPERATION_KEY = "LoggingAspect.operation";

    @Pointcut(value = "@annotation(loggable) && execution(* *(..))", argNames = "loggable")
    public void loggableMethod(Loggable loggable) {
        // Empty because it's just a pointcut
    }

    @Around(value = "loggableMethod(loggable)", argNames = "pjp, loggable")
    public Object logAccess(ProceedingJoinPoint pjp, Loggable loggable) throws Throwable {
        try {
            MDC.put(MDC_OPERATION_KEY, loggable.operation());
            log.info("Start operation");
            return pjp.proceed();
        } catch (Throwable throwable) {
            log.error(String.format("Error while executing operation { error: \"%s\"}", throwable.getMessage()), throwable);
            throw throwable;
        } finally {
            log.info("End operation");
            MDC.remove(MDC_OPERATION_KEY);
        }
    }
}
