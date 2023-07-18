package com.lv.rpc.test;

import com.lv.rpc.api.HelloObject;
import com.lv.rpc.api.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 15:23
 * @description ：
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(HelloObject object) {
        log.info("接收到消息: {}",object.getMessage());
        return "成功调用hello()方法+id:" + object.getId();

    }
}
