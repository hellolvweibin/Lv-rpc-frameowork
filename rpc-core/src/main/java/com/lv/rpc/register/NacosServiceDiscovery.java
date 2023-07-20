package com.lv.rpc.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lv.rpc.loadbalancer.LoadBalancer;
import com.lv.rpc.loadbalancer.RandomLoadBalancer;
import com.lv.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 16:48
 * @description ：服务发现实现类
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery{
    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if(loadBalancer == null){
            this.loadBalancer = new RandomLoadBalancer();
        }else {
            this.loadBalancer = loadBalancer;
        }

    }

    /**
     *  根据服务名称从注册中心获取到一个服务提供者的地址
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(),instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务时有错误发生", e);
        }
        return null;
    }
}
