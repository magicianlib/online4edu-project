package com.online4edu.dependencies.performance;

import com.online4edu.dependencies.performance.advice.MethodPerformanceMonitorAdvice;
import com.online4edu.dependencies.performance.properties.PerformanceMonitorConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MethodPerformanceMonitorAdvice.class})
@EnableConfigurationProperties(PerformanceMonitorConfigurationProperties.class)
public class PerformanceMonitorConfiguration {
}
