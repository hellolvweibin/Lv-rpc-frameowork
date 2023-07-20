package netty;

import com.lv.rpc.api.HelloService;
import com.lv.rpc.transport.netty.server.NettyServer;
import com.lv.rpc.serializer.KryoSerializer;
import com.lv.rpc.test.HelloServiceImpl;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 20:51
 * @description ：测试用Netty服务端
 */
public class NettyTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        NettyServer server = new NettyServer("127.0.0.1", 9999);
        server.setSerializer(new KryoSerializer());
        server.publishService(helloService, HelloService.class);
    }
}
