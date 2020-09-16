package com.su.hresource.util;

import com.su.hresource.annotation.NeedSetValueFeild;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 目标：关于bean中元素自动查询的工具
 *
 * @author Bboy_fork
 * @date 2020年8月21日15:03:58
 * */
@Component
public class BeantUtil implements ApplicationContextAware {

    /**
     * 缓存 之后替换为redis 现在先整个hashmap对付一下
     * */
    private HashMap<String,Object> cacheHashMap = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * 因多条查询或单条查询 进来的 其实就是返回值 --  pojo数组  / pojo 本身
     * 然后扫描其每个bean中的属性，对带注解的 进行查询 、塞值
     */
    public void setFileds(Object obj) throws ReflectiveOperationException {
        /**
         * 这里不管是 单个 拿出其属性值的数组
         * 还是迭代器 .next 拿到第一个的 其属性值的数组 总归是先用着 需要改【】【】【】【】【】【】【【】【】【】【】【】
         * */
        if(obj instanceof ArrayList){
            Iterator iterator = ((Collection) obj).iterator();
            while ( iterator.hasNext() ){
                setFiled( iterator.next() );
            }
        } else {
            setFiled(obj);
        }
    }

    /**
     * 遍历此类中的各个属性 对其中带注解的 进行查询填值处理
     * */
    public void setFiled(Object obj) throws ReflectiveOperationException {

        Class<?> clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field:fields) {
            NeedSetValueFeild setV = field.getAnnotation(NeedSetValueFeild.class);
            if(setV == null) continue;

            //开启可编辑
            field.setAccessible(true);

            //获取将要调取的类
            Object bean = this.applicationContext.getBean(setV.beanClass());

            //方法    :方法名 参数类型
            Method method = setV.beanClass().getMethod(setV.method(), clazz.getDeclaredField(setV.param()).getType());

            //参数
            Object invoke1 = clazz.getMethod(getUper(setV.param())).invoke(obj);

            //缓存key
            String cacheKey = setV.beanClass().getName() +"-"+setV.method()+"-"+invoke1;
            System.out.println("=====> "+cacheKey);

            Object returnValue = cacheHashMap.get(cacheKey);
            Object reObject;
            if(returnValue == null){
                System.out.println("缓存中没有，查询 塞值");
                //执行查询。 返回值
                reObject = method.invoke(bean, invoke1);
                cacheHashMap.put(cacheKey,reObject);
            }else {
                System.out.println("缓存中有 返回即可");
                reObject = returnValue;
            }

            //查不到就查不到吧
            if(reObject ==null|| reObject ==""){
                field.setAccessible(false);
                return;
            }

            //拿到需要的值
            Object targetInvoke = reObject.getClass().getMethod(getUper(setV.targetFiled())).invoke(reObject);

            //值塞到bean中
            clazz.getMethod(setUper(field.getName()),field.getType()).invoke(obj,targetInvoke);

            System.out.println(obj);

            //关闭可编辑
            field.setAccessible(false);
        }
    }

    /**
     * getXxx
     * */
    public String getUper(String str){
        return "get"+str.substring(0,1).toUpperCase()+str.substring(1);
    }

    /**
     * setXxx
     * */
    public String setUper(String str){
        return "set"+str.substring(0,1).toUpperCase()+str.substring(1);
    }

}
