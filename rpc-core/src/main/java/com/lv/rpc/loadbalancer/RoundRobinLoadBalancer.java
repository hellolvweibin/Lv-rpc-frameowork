package com.lv.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 20:30
 * @description ：轮询算法，按顺序来
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    /**
     * index表示当前选到了第几个服务器，并且每次选择后都会自增一
     */
    private int index = 0;

    @Override
    public Instance select(List<Instance> instances) {
        if (index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);

    }
}
