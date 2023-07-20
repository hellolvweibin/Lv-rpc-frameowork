package com.lv.rpc.provider;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 21:58
 * @description ：保存和提供服务实例对象
 */
public interface ServiceProvider {
    /**
     * @description 将一个服务注册到注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
    <T> void addServiceProvider(T service, Class<T> serviceClass);

     /**
      * @description 根据服务名获取服务实体
      */
     Object getServiceProvider(String serviceName);
}
