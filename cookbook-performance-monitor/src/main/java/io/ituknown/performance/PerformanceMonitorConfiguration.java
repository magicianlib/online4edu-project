package io.ituknown.performance;

import io.ituknown.performance.advice.MethodPerformanceMonitorAdvice;
import io.ituknown.performance.properties.PerformanceMonitorConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({MethodPerformanceMonitorAdvice.class})
@EnableConfigurationProperties(PerformanceMonitorConfigurationProperties.class)
public class PerformanceMonitorConfiguration {
}