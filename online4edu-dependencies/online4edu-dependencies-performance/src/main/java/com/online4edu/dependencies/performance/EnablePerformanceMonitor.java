package com.online4edu.dependencies.performance;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(EnablePerformanceMonitorImportSelector.class)
public @interface EnablePerformanceMonitor {
}
