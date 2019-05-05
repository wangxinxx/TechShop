package com.isliam.techshop.config;

import org.springframework.context.ApplicationContext;

public final class DIUtils {
    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        DIUtils.applicationContext = applicationContext;
    }

    public static Object getBean(Class<?> someClass){
        return applicationContext.getBean(someClass);
    }
}
