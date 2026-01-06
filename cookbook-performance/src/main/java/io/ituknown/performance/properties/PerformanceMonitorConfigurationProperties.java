package io.ituknown.performance.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "performance.monitor")
public class PerformanceMonitorConfigurationProperties {

    /**
     * 应用名称
     */
    @NotBlank
    private String appId = "trading.iapi.online4edu.cn";

    /**
     * 上报服务器地址
     */
    @NotBlank
    private String serverUrl = "performance-monitor.online4edu.cn";

    /**
     * 切点表达式
     */
    @NotEmpty
    private List<String> pointcutExpression = new ArrayList<>(Collections.singletonList("* com.example.demo.web..*Web.*(..)"));

}