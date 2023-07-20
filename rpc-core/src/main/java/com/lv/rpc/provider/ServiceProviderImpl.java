package com.lv.rpc.provider;

import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.spi.ServiceRegistry;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 22:04
 * @description ：默认的服务注册表，保存服务端本地服务
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {
    /**
     * key = 服务名称(即接口名), value = 服务实体(即实现类的实例对象)
     */
    private static final Map<String,Object> serviceMap = new ConcurrentHashMap<>();
    /**
     * 存放服务名称（接口名）
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    /**
     * @description 保存服务到本地服务注册表
     * @param [service, serviceClass] 服务的实现类对象，服务类（接口）
     * @return [void]
     */
    @Override
    public <T> void addServiceProvider(T service, String serviceName) {
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);
        serviceMap.put(serviceName, service);
        log.info("向接口：{} 注册服务：{}", service.getClass().getInterfaces(), serviceName);
    }

    @Override
    public Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
