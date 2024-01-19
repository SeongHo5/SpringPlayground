package com.seongho.spring.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 메서드의 실행 시간을 측정하는 어노테이션입니다.
 * 이 어노테이션을 메서드에 적용하면, 해당 메서드의 실행 시간이 로그로 기록됩니다.
 * 주로 성능 테스트나 디버깅 목적으로 사용됩니다.
 * 사용 후에는 어노테이션을 제거하는 것이 좋습니다.
 *
 * <p>사용 예:</p>
 * <pre>
 * &#64;ExeTimer
 * public void someMethod() {
 *     // 메서드 구현
 * }
 * </pre>
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExecutionTimer {

}
