package com.lv.rpc.hook;

import com.lv.facatory.ThreadPoolFactory;
import com.lv.util.NacosUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 19:09
 * @description ：注销服务的钩子
 */
@Slf4j
public class ShutdownHook {
    /**
     * 单例模式创建钩子，保证全局只有这个钩子
     */
    private static final ShutdownHook shutdownHook = new ShutdownHook();

    public static ShutdownHook getShutdownHook(){
        return shutdownHook;
    }

    /**
     * 注销服务的钩子
     */
    public void addClearAllHook(){
        log.info("服务端关闭前将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            NacosUtil.clearRegistry();
            //关闭所有线程池
            ThreadPoolFactory.shutDownAll();
        }));
    }
}
