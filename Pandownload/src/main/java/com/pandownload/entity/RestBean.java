package com.pandownload.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestBean<T> {
    public int status;
    public boolean success;
    public T message;

    public RestBean(int status, boolean success, T message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public static <T> RestBean<T> success(){
        return new RestBean<>(200, true, null);
    }

    public static <T> RestBean<T> success(T data){
        return new RestBean<>(200, true, data);
    }

    public static <T> RestBean<T> failure(int status){
        return new RestBean<>(status, false, null);
    }

    public static <T> RestBean<T> failure(int status, T data){
        return new RestBean<>(status, false, data);
    }
}
