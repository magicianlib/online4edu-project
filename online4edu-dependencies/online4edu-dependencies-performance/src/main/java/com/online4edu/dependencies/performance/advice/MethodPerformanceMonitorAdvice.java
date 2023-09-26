package com.online4edu.dependencies.performance.advice;

import com.online4edu.dependencies.performance.monitor.MethodPerformanceMonitor;
import com.online4edu.dependencies.performance.properties.PerformanceMonitorConfigurationProperties;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.StringJoiner;

@Configuration
public class MethodPerformanceMonitorAdvice {

    @Bean
    public MethodPerformanceMonitor methodMonitorAdvice(PerformanceMonitorConfigurationProperties properties) {
        MethodPerformanceMonitor monitor = new MethodPerformanceMonitor();
        monitor.setAppId(properties.getAppId());
        monitor.setServerUrl(properties.getServerUrl());
        return monitor;
    }

    @Bean
    public AspectJExpressionPointcut methodMonitorPointcut(PerformanceMonitorConfigurationProperties properties) {

        StringJoiner joiner = new StringJoiner(" || ");
        for (String expression : properties.getPointcutExpression()) {
            joiner.add(String.format("execution(%s)", expression));
        }
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(joiner.toString());
        return pointcut;
    }

    @Bean
    public Advisor methodPerformanceAdvice(PerformanceMonitorConfigurationProperties properties) {
        return new DefaultPointcutAdvisor(methodMonitorPointcut(properties), methodMonitorAdvice(properties));
    }
}
