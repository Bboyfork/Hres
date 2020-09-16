package com.su.hresource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注此注解 表示所修饰变量需要通过其他dao去查询填充值
 * @author Bboy_fork
 * @date 2020年8月21日15:35:19
 * */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedSetValueFeild {

    /**
     * 查询调取的接口类
     * */
    Class<?> beanClass();

    /**
     * 调取的方法
     * */
    String method();

    /**
     * 查询传入的参数
     * */
    String param();

    /**
     * 人话：要使用的 返回值中的参数
     *      --准备赋值给当前修饰注解的元素的值的来源
     * */
    String targetFiled();

}
