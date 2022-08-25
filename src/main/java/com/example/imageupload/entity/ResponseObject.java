package com.example.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

public class ResponseObject {
    private String status;
    private String description;
    private Object data;

    public ResponseObject(String status, String description, Object data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    public ResponseObject() {
    }
}
