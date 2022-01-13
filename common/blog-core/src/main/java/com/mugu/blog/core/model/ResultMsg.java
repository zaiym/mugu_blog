package com.mugu.blog.core.model;

import lombok.Data;

@Data
public class ResultMsg<T> {
    private Integer code;

    private String msg;

    private T data;

    public static <T> ResultMsg<T> resultSuccess(){
        return new ResultMsg<>(ResultCode.REQUEST_SUCCESS.getCode(),ResultCode.REQUEST_SUCCESS.getMsg(),null);
    }

    public static ResultMsg resultFail(){
        return new ResultMsg<>(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMsg(),null);
    }

    public static  ResultMsg resultFail(Integer code,String msg){
        return new ResultMsg<>(code,msg,null);
    }


    public static <T> ResultMsg<T> resultSuccess(T data){
        return new ResultMsg(ResultCode.REQUEST_SUCCESS.getCode(),ResultCode.REQUEST_SUCCESS.getMsg(),data);
    }

    private ResultMsg(){}

    public ResultMsg(Integer code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }


}
