package com.lv.rpc.transport;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 20:03
 * @description ：服务端类通用接口
 */
@Slf4j
public class RpcServer {
    private final ExecutorService threaPool;
    public RpcServer(){
        int corePoolSize = 5;
        int maximumPoolSize = 50;
        long keepAliveTime = 60;

        /**
         *设置上限为100个线程的阻塞队列
         */
        ArrayBlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        threaPool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,workingQueue,threadFactory);

    }
    public void register(Object service,int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            log.info("服务器正在启动...");
            Socket socket;
            //循环监听客户端的连接请求，accept会一直阻塞
            while((socket = serverSocket.accept())!=null){
                log.info("客户端连接成功！IP: {}",socket.getInetAddress());
                //创建工作线程响应请求
                threaPool.execute(new WorkerThread(socket,service));
            }

        } catch (IOException e) {
            log.error("连接时有错误发生：" + e);
        }
    }
}
