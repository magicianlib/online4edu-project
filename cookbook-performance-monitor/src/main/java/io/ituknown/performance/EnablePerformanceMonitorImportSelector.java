package io.ituknown.performance;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class EnablePerformanceMonitorImportSelector implements ImportSelector {

    private static final String[] IMPORTS = {
            PerformanceMonitorConfiguration.class.getName(),
    };

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        return IMPORTS;
    }
}