package netty;

import com.lv.rpc.api.HelloService;
import com.lv.rpc.netty.server.NettyServer;
import com.lv.rpc.register.DefaultServiceRegistry;
import com.lv.rpc.register.ServiceRegistry;
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
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.start(9999);
    }
}
