package netty;

import com.lv.rpc.RpcClient;
import com.lv.rpc.RpcClientProxy;
import com.lv.rpc.api.HelloObject;
import com.lv.rpc.api.HelloService;
import com.lv.rpc.netty.client.NettyClient;

import java.time.temporal.ValueRange;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 20:52
 * @description ：测试用Netty客户端
 */
public class NettyTestClient {
    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is netty style");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
