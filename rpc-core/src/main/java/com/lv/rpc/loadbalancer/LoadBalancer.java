package com.lv.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 20:27
 * @description ：负载均衡接口
 */
public interface LoadBalancer {
    /**
     * 从一系列Instance 中选择一个
     */
    Instance select(List<Instance> instances);
}
