package com.lv.rpc.transport.socket.server;

import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.facatory.ThreadPoolFactory;
import com.lv.rpc.transport.RpcServer;
import com.lv.rpc.handler.RequestHandler;
import com.lv.rpc.handler.RequestHandlerThread;
import com.lv.rpc.provider.ServiceProvider;
import com.lv.rpc.provider.ServiceProviderImpl;
import com.lv.rpc.register.NacosServiceRegistry;
import com.lv.rpc.register.ServiceRegistry;
import com.lv.rpc.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 14:39
 * @description ：Socket方式进行远程调用连接的客户端
 */
@Slf4j
public class SocketServer implements RpcServer {


    private final ExecutorService threadPool;
    private final String host;
    private final int port;
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();

    private final ServiceRegistry serviceRegistry;
    private final ServiceProvider serviceProvider;

    public SocketServer(String host, int port){
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        //创建线程池
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
    }

    /**
     * @description 将服务保存在本地的注册表，同时注册到Nacos注册中心
     * @param [service, serviceClass]
     * @return [void]
     * @date [2021-03-13 16:53]
     */
    @Override
    public <T> void publishService(Object service, Class<T> serviceClass) {
        if (serializer == null){
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }

    /**
     * @description 服务端启动
     * @return [void]
     * @date [2021-02-05 11:57]
     */
    @Override
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            log.info("服务器启动……");
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                log.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            log.info("服务器启动时有错误发生：" + e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }

}
