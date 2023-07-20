package com.lv.rpc.test;

import com.lv.rpc.annotation.Service;
import com.lv.rpc.api.ByeService;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 21:16
 * @description ：服务实现类
 */
@Service
public class ByeServiceImpl implements ByeService {
    @Override
    public String bye(String name) {
        return "bye," + name;
    }
}
