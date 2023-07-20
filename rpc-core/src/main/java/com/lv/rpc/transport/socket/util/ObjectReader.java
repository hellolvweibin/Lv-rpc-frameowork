package com.lv.rpc.transport.socket.util;

import com.lv.entity.RpcRequest;
import com.lv.entity.RpcResponse;
import com.lv.enumeration.PackageType;
import com.lv.enumeration.RpcError;
import com.lv.exception.RpcException;
import com.lv.rpc.serializer.CommonSerializer;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/20 11:07
 * @description ：Socket方式从输入流中读取字节并反序列化【解码】
 */
@Slf4j
public class ObjectReader {
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static Object readObject(InputStream in) throws IOException {
        byte[] numberBytes = new byte[4];
        //先读取四字节长度的数据
        in.read(numberBytes);
        int magic = bytesToInt(numberBytes);
        if(magic!=MAGIC_NUMBER){
            log.error("不识别的协议包：{}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        in.read(numberBytes);
        //判断数据包的类型
        int packageCode = bytesToInt(numberBytes);
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            log.error("不识别的数据包：{}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        //判断序列化器类型
        in.read(numberBytes);
        int serializerCode = bytesToInt(numberBytes);
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if (serializer == null) {
            log.error("不识别的反序列化器：{}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        in.read(numberBytes);
        int length = bytesToInt(numberBytes);
        byte[] bytes = new byte[length];
        in.read(bytes);
        return serializer.deserialize(bytes, packageClass);



    }


    /**
     * @description 字节数组转换为Int
     * @param src
     * @return [int]
     */
    private static int bytesToInt(byte[] src) {
        int value;
        value = (src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24);
        return value;
    }
}
