package com.lv.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project ：Lv-rpc-framework
 * @Author ：Levi_Bee
 * @Date ：2023/7/19 19:11
 * @description ：包类型枚举
 */
@Getter
@AllArgsConstructor
public enum PackageType {
    REQUEST_PACK(0),
    RESPONSE_PACK(1);

    private final int code;
}
