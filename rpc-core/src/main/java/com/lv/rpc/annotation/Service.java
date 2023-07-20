package com.lv.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 20:47
 * @description ：用来标识一个服务提供类，注解放在接口的实现类上
 */
//表示注解的作用目标为接口、类、枚举
@Target(ElementType.TYPE)
//表示在运行时可以动态获取注解信息
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    public String name() default "";
}
