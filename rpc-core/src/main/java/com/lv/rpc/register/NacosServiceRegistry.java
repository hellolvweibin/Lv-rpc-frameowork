package com.lv.rpc.register;


import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.util.NacosUtil;
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
public class NacosServiceRegistry implements ServiceRegistry{

    public final NamingService namingService;

    public NacosServiceRegistry(){
        namingService = NacosUtil.getNacosNamingService();
    }

    /**
     * @description 将服务的名称和地址注册进服务注册中心
     * @param [serviceName, inetSocketAddress]
     * @return [void]
     * @date [2021-03-13 15:40]
     */
    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            //向Nacos注册服务
            NacosUtil.registerService(namingService, serviceName, inetSocketAddress);
        }catch (NacosException e) {
            log.error("注册服务时有错误发生" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
