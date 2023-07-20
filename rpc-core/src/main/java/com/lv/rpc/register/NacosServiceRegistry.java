package com.lv.rpc.register;


import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 14:43
 * @description ： Nacos服务注册中心实现类
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {
    private static final String SERVER_ADDR = "127.0.0.1:8849";
    private static final NamingService namingService;

    static {
        try {
            //连接Nacos创建命名服务
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            log.error("连接Nacos时有错误发生：" + e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }


    /**
     * 将服务的名称和地址注册到服务注册中心
     */
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos 注册服务
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            log.error("注册服务时有错误发生" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }

    }

    /**
     * 根据服务名称从注册中心获取到一个服务提供者的地址
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try{
            //利用列表获取某个服务的所有提供者
            List<Instance> instances = namingService.getAllInstances(serviceName);
            //通过getAllInstance()获取到某个服务的所有提供者，然后需要选择一个，这里就涉及到负载均衡策略,这里直接选择第一个
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生" + e);
        }
        return null;
    }
}
