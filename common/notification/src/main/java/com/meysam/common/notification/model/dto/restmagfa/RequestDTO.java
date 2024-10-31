package com.meysam.common.notification.model.dto.restmagfa;

import lombok.Data;

@Data
public class RequestDTO<T> {
    T data;

    public RequestDTO() {
    }

    public RequestDTO(T data) {
        this.data = data;
    }
}
