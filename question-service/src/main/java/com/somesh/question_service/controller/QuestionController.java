package com.somesh.question_service.controller;

import com.somesh.question_service.models.Question;
import com.somesh.question_service.models.QuestionWrapper;
import com.somesh.question_service.models.Response;
import com.somesh.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private Environment environment;
    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String category_name, @RequestParam Integer num_questions){
        return questionService.getQuestionsForQuiz(category_name,num_questions);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port")); //prints the port number
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/getScore")
    public ResponseEntity<Integer>  getScore(@RequestBody List<Response> responses){
        responses.stream().forEach(System.out::println);
        return questionService.getScore(responses);
    }
}
