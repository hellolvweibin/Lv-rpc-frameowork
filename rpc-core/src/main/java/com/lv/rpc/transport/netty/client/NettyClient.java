package com.lv.rpc.transport.netty.client;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.rpc.register.NacosServiceDiscovery;
import com.lv.rpc.register.ServiceDiscovery;
import com.lv.rpc.transport.RpcClient;
import com.lv.rpc.register.NacosServiceRegistry;
import com.lv.rpc.register.ServiceRegistry;
import com.lv.rpc.serializer.CommonSerializer;
import com.lv.util.RpcMessageChecker;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 15:44
 * @description ： Netty方式客户端
 */
@Slf4j
public class NettyClient implements RpcClient {

    private static final EventLoopGroup group;
    private static final Bootstrap bootstrap;

    static {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;

    public NettyClient() {
        //以默认序列化器调用构造函数
        this(DEFAULT_SERIALIZER);
    }

    public NettyClient(Integer serializerCode){
        serviceDiscovery = new NacosServiceDiscovery();
        serializer = CommonSerializer.getByCode(serializerCode);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if (serializer == null) {
            log.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        //保证自定义实体类变量的原子性和共享性的线程安全，此处应用于rpcResponse
        AtomicReference<Object> result = new AtomicReference<>(null);
        try {
            //从Nacos获取提供对应服务的服务端地址
            InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
            //创建Netty通道连接
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if(!channel.isActive()) {
                group.shutdownGracefully();
                return null;
            }
            //向服务端发请求，并设置监听，关于writeAndFlush()的具体实现可以参考：https://blog.csdn.net/qq_34436819/article/details/103937188
            channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                if(future1.isSuccess()){
                    log.info(String.format("客户端发送消息：%s", rpcRequest.toString()));
                }else {
                    log.error("发送消息时有错误发生:", future1.cause());
                }
            });
            channel.closeFuture().sync();
            //AttributeMap<AttributeKey, AttributeValue>是绑定在Channel上的，可以设置用来获取通道对象
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
            //get()阻塞获取value
            RpcResponse rpcResponse = channel.attr(key).get();
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            result.set(rpcResponse.getData());
        }catch (InterruptedException e){
            log.error("发送消息时有错误发生:", e);
            //interrupt()这里作用是给受阻塞的当前线程发出一个中断信号，让当前线程退出阻塞状态，好继续执行然后结束
            Thread.currentThread().interrupt();
        }
        return result.get();
    }

}

