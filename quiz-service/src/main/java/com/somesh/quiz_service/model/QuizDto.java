package com.somesh.quiz_service.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class QuizDto {
    String categoryName;
    Integer numQuestions;
    String title;
}
