import com.lv.rpc.api.HelloService;
import com.lv.rpc.serializer.KryoSerializer;
import com.lv.rpc.test.HelloServiceImpl;
import com.lv.rpc.transport.socket.server.SocketServer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 20:57
 * @description ：服务端测试/创建一个RpcServer把这个实现类注册进去，启动服务端，监听请求进行处理即可
 */
public class SocketTestServer {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9998);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.publishService(helloService, HelloService.class);
    }
}
