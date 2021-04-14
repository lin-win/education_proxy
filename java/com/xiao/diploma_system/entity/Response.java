package com.xiao.diploma_system.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义返回的Json数据格式
 *
 * @param <T> 响应数据部分的类型
 */
@Getter
@Setter
public class Response<T> {
    /**
     * 状态码
     */
    private int code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
//        System.out.println(code);
    }
    public int getcode(){
        return code;
    }

}
