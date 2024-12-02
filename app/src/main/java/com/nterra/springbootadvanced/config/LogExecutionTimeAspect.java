package com.nterra.springbootadvanced.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class LogExecutionTimeAspect {

  @Around("@annotation(LogExecutionTime)")
  public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch(joinPoint.getSignature().toLongString());
    stopWatch.start();
    Object[] argumentsOfAnnotatedMethod = joinPoint.getArgs();
    Object result = joinPoint.proceed();
    stopWatch.stop();
    log.info(stopWatch.shortSummary());
    return result;
  }

}
