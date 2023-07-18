package com.lv.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/18 15:49
 * @description ：响应状态码对象
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {
    /**
     * 返回信息代码
     */
    SUCCESS(200,"调用方法成功"),
    FAIL(500, "调用方法失败"),
    METHOD_NOT_FOUND(500, "未找到指定方法"),
    ClASS_NOT_FOUND(500, "未找到指定类");


    private final int code;
    private final String message;
}
