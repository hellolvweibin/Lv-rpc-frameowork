package com.lv.rpc.netty.client;

import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.rpc.codec.CommonDecoder;
import com.lv.rpc.codec.CommonEncoder;
import com.lv.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 13:47
 * @description ：用于获取Channel对象
 */
@Slf4j
public class ChannelProvider {
    private static EventLoopGroup eventLoopGroup;
    private static Bootstrap bootstrap = initializeBootstrap();
    private static final int MAX_RETRY_COUNT = 5;
    private static Channel channel = null;


    private static Bootstrap initializeBootstrap() {
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                //连接超时时间，超过这个时间还是连接不上代表连接失败
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，默认的心跳间隔是7200s即2小时。
                .option(ChannelOption.SO_KEEPALIVE, true)
                //配置Channel参数，nodelay没有延迟，true就代表禁用Nagle算法，减小传输延迟。
                .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }

    public static Channel get(InetSocketAddress inetSocketAddress, CommonSerializer serializer) {
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new CommonEncoder(serializer))
                        .addLast(new CommonDecoder())
                        .addLast(new NettyClientHandler());
            }
        });
        //设置计数器值为1
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            connect(bootstrap,inetSocketAddress,countDownLatch);
            //阻塞当前线程直到计时器的值为0
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("获取Channel时有错误发生", e);
        }
        return channel;
    }
    private static void connect(Bootstrap bootstrap,InetSocketAddress inetSocketAddress,CountDownLatch countDownLatch){
        connect(bootstrap,inetSocketAddress,MAX_RETRY_COUNT,countDownLatch);
    }

    /**
     * @description Netty客户端创建通道连接,实现连接失败重试机制
     * @param bootstrap, inetSocketAddress, retry, countDownLatch
     * @return [void]
     */
    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, int retry, CountDownLatch countDownLatch) {
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("客户端连接成功");
                channel = future.channel();
                //计数器减1
                countDownLatch.countDown();
                return;
            }
            if (retry == 0) {
                log.error("客户端连接失败，重试次数已用完，放弃连接！");
                countDownLatch.countDown();
                throw new RpcException(RpcError.CLIENT_CONNECT_SERVER_FAILURE);
            }
            //第几次重连
            int order = (MAX_RETRY_COUNT - retry) + 1;
            //重连的时间间隔，相当于1乘以2的order次方
            int delay = 1 << order;
            log.error("{}:连接失败，第「{}次重连....", new Date(), order);
            //利用schedule()在给定的延迟时间后执行connect()重连
            bootstrap.config().group().schedule(() -> connect(bootstrap, inetSocketAddress, retry - 1, countDownLatch), delay, TimeUnit.SECONDS);
        });

    }


}
