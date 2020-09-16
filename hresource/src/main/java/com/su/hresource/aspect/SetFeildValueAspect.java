package com.su.hresource.aspect;

import com.su.hresource.util.BeantUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Bboy_fork
 * @date 2020年8月21日15:20:12
 * */
@Component
@Aspect
public class SetFeildValueAspect {

    @Autowired
    BeantUtil beantUtil;

    /**
     * 所有标注了@NeedSetValue注解 的方法 都需要进行的切面操作
     *      ：填充值
     * */
    @Around("@annotation(com.su.hresource.annotation.NeedSetValue)")
    public Object doSetFeildValue(ProceedingJoinPoint pjp) throws Throwable{
        Object returnObj = pjp.proceed();

        //后续操作  将其returnObj中 添加过NeedSetValueFeild 注解的进行调取其注解上的方法 进行查询处理。
        beantUtil.setFileds(returnObj);
        return returnObj;
    }
}
