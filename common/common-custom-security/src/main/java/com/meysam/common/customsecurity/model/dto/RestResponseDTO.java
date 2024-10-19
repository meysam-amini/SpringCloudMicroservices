package com.meysam.common.customsecurity.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestResponseDTO<T> {

    boolean err;
    int code;
    String message;
    T data;

    public RestResponseDTO(boolean isError, int code, T data) {
        this.err = isError;
        this.message = isError ? "error" : "success";
        this.code = code;
        this.data = data;
    }

    public RestResponseDTO(boolean err, int code, String message, T data) {
        this.err = err;
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public static <T> RestResponseDTO<T> generate(boolean isError, int code, String msg, T data) {
        return new RestResponseDTO(isError, code,msg, data);
    }

    public static <T> RestResponseDTO<T> generate(boolean isError, int code, String msg) {
        return new RestResponseDTO(isError, code,msg, "");
    }

    public static <T> RestResponseDTO<T> generate(boolean isError, int code, T data) {
        return new RestResponseDTO(isError, code,"msg", data);
    }
    public static <data> RestResponseDTO success(data data,String msg){
        return generate(false,200,msg,data);
    }
}
