package com.su.hresource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注此注解 表示其结果需要进行填充 具体填充 为其bean中元素的注解上标注
 * @author Bboy_fork
 * @date 2020年8月21日15:35:19
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedSetValue {
}
