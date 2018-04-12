package com.cunpiao.network.exception;


import com.cunpiao.network.RespCodeEnum;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-07-06 14:23
 * @DESCRIPTION:
 *                  自定义异常
 */
public class BllException extends Throwable {
    private String code;
    private String msg;
    public BllException(){
    }

    public BllException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }


    public BllException(RespCodeEnum codeEnum){
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
