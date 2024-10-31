package org.taba.notification.model.dto.restmagfa;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseDTO<T> {

    boolean err;
    String code;
    String message;
    ArrayList<String> messages;
    T data;

    public ResponseDTO(boolean isError,String code, T data) {
        this.err = isError;
        this.message = isError ? "error" : "success";
        this.code = code;
        this.data = data;
    }

    public static  <T> ResponseDTO<T> generate(boolean isError, String code,  T data){
        return new ResponseDTO(isError, code, data);
    }
}
