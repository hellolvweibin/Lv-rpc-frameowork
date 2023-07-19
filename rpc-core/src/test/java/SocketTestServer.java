import com.lv.rpc.api.HelloService;
import com.lv.rpc.register.DefaultServiceRegistry;
import com.lv.rpc.socket.server.SocketServer;
import com.lv.rpc.test.HelloServiceImpl;
import com.lv.rpc.transport.RpcServer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 20:57
 * @description ：服务端测试/创建一个RpcServer把这个实现类注册进去，启动服务端，监听请求进行处理即可
 */
public class SocketTestServer {
    public static void main(String[] args) {
        //创建服务对象
        HelloService helloService = new HelloServiceImpl();
        //创建服务容器
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        //注册服务对象到服务容器中
        serviceRegistry.register(helloService);
        //将服务容器纳入到服务端
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.start(9000);

    }
}
