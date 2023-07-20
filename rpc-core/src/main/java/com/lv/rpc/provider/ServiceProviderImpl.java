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
     * 用来存放实现类的名称，Set存取更高效，存放实现类名称相比存放接口名称占的空间更小，因为一个实现类可能实现了多个接口
     */
    private static final Set<String> registerService = ConcurrentHashMap.newKeySet();

    @Override
    public synchronized  <T> void addServiceProvider(T service) {
        //getCanonicalName返回由 Java 语言规范定义的基础类的规范名称
        String serviceImplName = service.getClass().getCanonicalName();
        if(registerService.contains(serviceImplName)){
            return;
        }
        registerService.add(serviceImplName);
        //可能实现了多个接口，用数组来保存
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0){
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(),service);
        }
        log.info("向接口：{} 注册服务：{}",interfaces,service);

    }

    @Override
    public synchronized Object getServiceProvider(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(service == null){
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
