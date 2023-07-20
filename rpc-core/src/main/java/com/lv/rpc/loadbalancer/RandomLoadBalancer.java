package com.lv.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 20:29
 * @description ：随机一个
 */
public class RandomLoadBalancer implements LoadBalancer{
    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }
}
