package com.minsoo.co.tireerpserver.model.dto;

import lombok.Data;

@Data
public class ResponseDTO<T> {

    private T contents;

    public ResponseDTO(T contents) {
        this.contents = contents;
    }
}
