package com.superheroes.SuperheroesApp.models.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimedAspect {

    private long startTime;

    @Before("@annotation(com.superheroes.SuperheroesApp.models.aspect.Timed)")
    public void before(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @After("@annotation(com.superheroes.SuperheroesApp.models.aspect.Timed)")
    public void after(JoinPoint joinPoint) {
        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Method executed in " + elapsedTime + " ms: " + joinPoint.getSignature());
    }
}