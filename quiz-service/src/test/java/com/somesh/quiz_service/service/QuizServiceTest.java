package com.somesh.quiz_service.service;

import com.somesh.quiz_service.dao.QuizDao;
import com.somesh.quiz_service.feign.QuizInterface;
import com.somesh.quiz_service.model.Quiz;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    QuizDao quizDao;

    @Mock
    QuizInterface quizInterface;

    @InjectMocks
    QuizService quizService;

    @Test
    void createQuiz_Success() {
        List<Integer> questions=List.of(1,2);
        when(quizInterface.getQuestionsForQuiz("database",2))
                .thenReturn(ResponseEntity.of(Optional.of(questions)));

        Quiz quiz=new Quiz();
        quiz.setTitle("programming");
        quiz.setQuestionIds(questions);

        ResponseEntity<String> response=quizService.createQuiz("database",2,"programming");

        ArgumentCaptor<Quiz> argumentCaptor=ArgumentCaptor.forClass(Quiz.class);
        verify(quizDao,times(1)).save(argumentCaptor.capture());
        Quiz savedQuiz=argumentCaptor.getValue();

        assertEquals(quiz,savedQuiz);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals("Success",response.getBody());
        //assertEquals(List.of(1,2),quizService.createQuiz("database",2,"programming").getBody());
    }

    @Test
    void getQuizQuestions() {
    }

    @Test
    void calculateScore() {
    }
}