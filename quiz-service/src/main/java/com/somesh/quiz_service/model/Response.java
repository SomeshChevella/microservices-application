package com.somesh.quiz_service.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Response {
    private Integer id;
    private String response;
}
