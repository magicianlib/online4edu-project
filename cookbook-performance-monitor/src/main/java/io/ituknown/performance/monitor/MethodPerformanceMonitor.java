package io.ituknown.performance.monitor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

public class MethodPerformanceMonitor implements MethodInterceptor, Ordered,
        InitializingBean, DisposableBean, ApplicationContextAware {

    private ApplicationContext context;

    private String appId;
    private String serverUrl;

    @Override
    public void destroy() throws Exception {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(appId);
        System.out.println(serverUrl);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object that = invocation.getThis();
        Method method = invocation.getMethod();

        String clazzName = method.getDeclaringClass().getSimpleName();

        // 如果是接口方法, 进一步定位到具体实现类
        // 动态代理类的类名类似 "$Proxy34", 无意义
        if (method.getDeclaringClass().isInterface() && Objects.nonNull(that) && !Proxy.isProxyClass(that.getClass())) {
            clazzName = that.getClass().getSimpleName();
        }

        long start = System.currentTimeMillis();

        String fullMethod = clazzName + "#" + method.getName();

        try {
            return invocation.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            System.out.println(fullMethod + " : " + elapsed);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}