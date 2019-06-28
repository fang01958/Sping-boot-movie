package com.fang.movie.dto;

public class ResponseDTO {

    public static final int error_code = 9999;
    public static final int success_code = 1000;
    public static final int login_code = 666;

    private int code = success_code;
    private String message;
    private Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(Object data) {
        this.data = data;
    }

    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseDTO relogin(){

        return new ResponseDTO(login_code,"重新登录");
    }

    public static ResponseDTO success(){

        return new ResponseDTO();
    }

    public static ResponseDTO fail(String message){

        return new ResponseDTO(error_code,message);
    }

    public static ResponseDTO success(Object data){

        return new ResponseDTO(data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
