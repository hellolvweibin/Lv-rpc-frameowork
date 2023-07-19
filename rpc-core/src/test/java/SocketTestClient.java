import com.lv.rpc.api.HelloObject;
import com.lv.rpc.api.HelloService;
import com.lv.rpc.RpcClientProxy;
import com.lv.rpc.socket.client.SocketClient;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 20:57
 * @description ：客户端测试/通过动态代理生成代理对象，并调用接口方法，动态代理自动帮我们向服务端发送请求，返回结果
 */
public class SocketTestClient {
    public static void main(String[] args) {

        SocketClient client = new SocketClient("127.0.0.1", 9000);
        //接口与代理对象之间的中介对象
        RpcClientProxy proxy = new RpcClientProxy(client);
        //创建代理对象
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is test message");
        //由动态代理可知，代理对象调用hello()实际会执行invoke()
        String res = helloService.hello(object);
        System.out.println(res);

    }

}
