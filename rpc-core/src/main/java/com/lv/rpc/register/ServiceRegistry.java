package com.lv.rpc.register;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 21:58
 * @description ：服务注册接口
 */
public interface ServiceRegistry {
    /**
     * @description 将一个服务注册到注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
     <T> void register(T service);

     /**
      * @description 根据服务名获取服务实体
      */
     Object getService(String serviceName);
}
