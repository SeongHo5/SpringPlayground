package com.seongho.spring.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 이 유틸리티 클래스는 주로 스프링의 의존성 주입이 자동으로 이루어지지 않는 경우에 사용됩니다.
 *  예를 들어, JPA 엔티티 리스너와 같이 스프링 라이프사이클 밖에서
 * 생성되는 객체들이 스프링 빈에 접근해야 할 때 이 클래스의 메서드를 활용할 수 있습니다.
 * <p>
 * {@code getBean(Class<T> beanClass)} 메서드를 사용하여
 * 스프링 애플리케이션 컨텍스트에서 특정 타입의 빈을 직접 조회할 수 있습니다.
 */
@Component
public class BeansUtil implements ApplicationContextAware {

    private static ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
