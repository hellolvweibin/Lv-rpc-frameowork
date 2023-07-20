package com.lv.facatory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 20:37
 * @description ：单例工厂，保证一个类只有一个实例，节约资源，保证线程安全
 */
public class SingletonFactory {
    private static Map<Class,Object> objectMap = new HashMap<>();

    private SingletonFactory() {
    }
    public  static  <T> T getInstance(Class<T> clazz){
        Object instance = objectMap.get(clazz);
        synchronized (clazz){
            if(instance == null){
                try {
                    instance = clazz.newInstance();
                    objectMap.put(clazz,instance);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }
}
