package com.seongho.spring.aop.executor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {

    @Pointcut("@annotation(com.seongho.spring.aop.annotation.ExecutionTimer)")
    private void timer() {
    }

    @Around("timer()")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        Object proceed;
        String methodSignature = joinPoint.getSignature().getName();

        try {
            stopWatch.start();
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception occurred while measuring execution time of method: {}", methodSignature);
            throw throwable;
        } finally {
            stopWatch.stop();
            log.info("{} elapsed time : {} ms", methodSignature, stopWatch.getTotalTimeMillis());
        }

        return proceed;
    }
}
