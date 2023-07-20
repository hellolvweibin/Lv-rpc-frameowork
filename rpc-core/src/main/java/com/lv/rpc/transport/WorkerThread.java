//package com.lv.rpc.transport;
//
//import com.lv.entity.RpcRequest;
//import com.lv.entity.RpcResponse;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.Socket;
//import java.net.SocketException;
//
///**
// * @Project ：Lv-rpc-framework
// * @Author ：Levi_Bee
// * @Date ：2023/7/18 20:40
// * @description ：
// */
//@Slf4j
//public class WorkerThread implements Runnable{
//    private Socket socket;
//    private Object service;
//
//    public WorkerThread(Socket socket, Object service) {
//        this.socket = socket;
//        this.service = service;
//    }
//
//    @Override
//    public void run() {
//        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
//            //获得来自客户端的请求
//            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
//            //利用反射原理找到远程所需调用的方法
//            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
//
//            //invoke(obj实例对象,obj可变参数)
//            Object returnObject = method.invoke(service, rpcRequest.getParameters());
//            objectOutputStream.writeObject(RpcResponse.success(returnObject));
//            objectOutputStream.flush();
//
//        } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
//                 InvocationTargetException e) {
//            log.error("调用或发送时有错误发生:"+e);
//        }
//
//    }
//}
