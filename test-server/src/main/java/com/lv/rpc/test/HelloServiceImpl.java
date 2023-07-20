package com.lv.rpc.test;

import com.lv.rpc.annotation.Service;
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
@Service
public class HelloServiceImpl implements HelloService {

    /**
     * 使用HelloServiceImpl初始化日志对象，方便在日志输出的时候，可以打印出日志信息所属的类。
     */
    @Override
    public String hello(HelloObject object) {
        //使用{}可以直接将getMessage()内容输出
        log.info("接收到消息：{}", object.getMessage());
        return "成功调用hello()方法";
    }
}
