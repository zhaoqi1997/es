package com.zq.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhaoqi
 * @version 1.8
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static  ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    public static Object getBean(String id){
        Object bean = applicationContext.getBean(id);
        return bean;
    }
}
