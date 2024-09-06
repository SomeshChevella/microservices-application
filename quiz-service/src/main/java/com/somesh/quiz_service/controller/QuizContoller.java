package com.somesh.quiz_service.controller;

import com.somesh.quiz_service.model.QuestionWrapper;
import com.somesh.quiz_service.model.QuizDto;
import com.somesh.quiz_service.model.Response;
import com.somesh.quiz_service.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizContoller {

    private QuizService quizService;

    public QuizContoller(QuizService quizService) {
        this.quizService=quizService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
        return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateScore(id,responses);
    }
}
