package com.cccis.base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * springBeanUtils工具类
 */
@Component("springBeanUtils")
public class SpringBeanUtils implements ApplicationContextAware {

    /**
     * spring上下文中.
     */
    private static ApplicationContext applicationContext;

    /**
     * 从spring上下文中的bean的id获取bean对象.
     *
     * @param beanID springBean对象id
     * @return Object
     */
    public static Object getBean(final String beanID) {
        return applicationContext.getBean(beanID);
    }

    /**
     * 通过请求泛型的类对象获取spring上下文中的bean对象.
     *
     * @param <T>           泛型
     * @param requiredClass 请求类泛型
     * @return 泛型
     */
    public static <T> T getBean(final Class<T> requiredClass) {
        return applicationContext.getBean(requiredClass);
    }

    /**
     * 通过指定名字和类型取得Bean实例
     *
     * @param name         bean的名字
     * @param requiredType bean的类型
     * @param <T>          类型参数
     * @return bean实例
     */
    public static <T> T getBean(final String name, final Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        SpringBeanUtils.applicationContext = applicationContext;
    }
}
