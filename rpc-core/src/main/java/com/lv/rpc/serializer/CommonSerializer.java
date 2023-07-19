package com.lv.rpc.serializer;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 19:15
 * @description ：通用的序列化反序列化接口
 */
public interface CommonSerializer {
    byte[] serialize(Object obj);
    Object deserialize(byte[] bytes, Class<?> clazz);
    int getCode();

    static CommonSerializer getByCode(int code){
        switch (code){
            case 1:
                return new JsonSerializer();
            default:
                return null;
        }
    }
}
