package com.somesh.quiz_service.service;

import com.somesh.quiz_service.dao.QuizDao;
import com.somesh.quiz_service.feign.QuizInterface;
import com.somesh.quiz_service.model.QuestionWrapper;
import com.somesh.quiz_service.model.Quiz;
import com.somesh.quiz_service.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private QuizDao quizDao;
    private QuizInterface quizInterface;
    public QuizService(QuizDao quizDao, QuizInterface quizInterface){
        this.quizDao=quizDao;
        this.quizInterface=quizInterface;
    }

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions= quizInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz= new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        Quiz quiz= quizDao.findById(id).get();
        List<Integer> questionIds= quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions= quizInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateScore(Integer id, List<Response> responses) {
        return quizInterface.getScore(responses);
    }
}
