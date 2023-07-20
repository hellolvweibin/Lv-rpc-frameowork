package com.lv.rpc.transport.socket.server;

import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.facatory.ThreadPoolFactory;
import com.lv.rpc.hook.ShutdownHook;
import com.lv.rpc.transport.AbstractRpcServer;
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
public class SocketServer extends AbstractRpcServer {

    private final ExecutorService threadPool;
    private final CommonSerializer serializer;
    private final RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializerCode){
        this.host = host;
        this.port = port;
        serviceRegistry = new NacosServiceRegistry();
        serviceProvider = new ServiceProviderImpl();
        serializer = CommonSerializer.getByCode(serializerCode);
        //创建线程池
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        //自动注册服务
        scanServices();
    }

    /**
     * @description 服务端启动
     * @param []
     * @return [void]
     * @date [2021-02-05 11:57]
     */
    @Override
    public void start(){
        try(ServerSocket serverSocket = new ServerSocket()){
            serverSocket.bind(new InetSocketAddress(host, port));
            log.info("服务器启动……");
            //添加钩子，服务端关闭时会注销服务
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            //当未接收到连接请求时，accept()会一直阻塞
            while ((socket = serverSocket.accept()) != null){
                log.info("客户端连接！{}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        }catch (IOException e){
            log.info("服务器启动时有错误发生：" + e);
        }
    }

}
