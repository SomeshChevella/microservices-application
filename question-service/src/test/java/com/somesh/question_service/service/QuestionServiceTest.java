package com.somesh.question_service.service;

import com.somesh.question_service.dao.QuestionDao;
import com.somesh.question_service.models.Question;
import com.somesh.question_service.models.QuestionWrapper;
import com.somesh.question_service.models.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    QuestionDao questionDao;

    @InjectMocks
    QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
    }

    static List<Question> questions= new ArrayList<>();;
    Question question1=new Question(4, "Non Relational Database",
            "PostgreSQL", "MongoDB", "MySQL", "RDS", "MongoDB", "easy", "database");

    @BeforeAll
    static void intialize(){
        questions.add(new Question(1,"Select the Object oriented language",
                "Java","SQL","C","COBOL","Java","easy","programming"));
        questions.add(new Question(2,"Relational Database",
                "PostgreSQL","MongoDB","Cassandra","S3","PostgreSQL","easy","database"));
        questions.add(new Question(3, "Filebased storage",
                "PostgreSQL", "MongoDB", "S3", "RDS", "S3", "easy", "database"));
    }

    @Test
    void getAllQuestions() {
        when(questionDao.findAll()).thenReturn(questions);
        ResponseEntity<List<Question>> response= questionService.getAllQuestions();
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(questions,response.getBody());
    }

    @Test
    void getAllQuestions_Exception(){
        when(questionDao.findAll()).thenThrow(new RuntimeException("Mocked Exception"));

        ResponseEntity<List<Question>>  listOfQuestions=questionService.getAllQuestions();
        assertEquals(HttpStatus.BAD_REQUEST,listOfQuestions.getStatusCode());
        assertEquals(new ArrayList<>(),listOfQuestions.getBody());
    }

    @Test
    void getQuestionsByCategory() {
        when(questionDao.findByCategory("database")).thenReturn(questions.stream()
                                                    .filter(question -> question.getCategory().equals("database")).collect(Collectors.toList()));

        ResponseEntity<List<Question>> questionList=questionService.getQuestionsByCategory("database");
        assertEquals(HttpStatus.OK,questionList.getStatusCode());
        assertEquals(List.of(questions.get(1),questions.get(2)),questionList.getBody());
    }

    @Test
    void getQuestionsByCategory_Exception(){
        when(questionDao.findByCategory("database")).thenThrow(new RuntimeException("Mocked Exception"));
        ResponseEntity<List<Question>> listOfQuestions=questionService.getQuestionsByCategory("database");

        assertEquals(HttpStatus.BAD_REQUEST,listOfQuestions.getStatusCode());
        assertEquals(new ArrayList<>(),listOfQuestions.getBody());
    }

    @Test
    void addQuestion() {
        ResponseEntity<String> response = questionService.addQuestion(question1);

        verify(questionDao,times(1)).save(question1);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals("success",response.getBody());
    }

    @Test
    void getQuestionsForQuiz() {
        when(questionDao.findRandomQuestionsByCategory("database",2)).thenReturn(List.of(2,3));
        ResponseEntity<List<Integer>> quiz=questionService.getQuestionsForQuiz("database",2);
        assertEquals(List.of(2,3),quiz.getBody());
        assertEquals(HttpStatus.OK,quiz.getStatusCode());
    }

    @Test
    void testGetQuestionsFromId_Success() {
        List<Integer> questionIds=new ArrayList<>();
        questionIds.add(1);

        when(questionDao.findById(1)).thenReturn(Optional.of(question1));
        ResponseEntity<List<QuestionWrapper>> response= questionService.getQuestionsFromId(questionIds);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(1,response.getBody().size());
        assertEquals(question1.getQuestionTitle(),response.getBody().get(0).getQuestionTitle());
    }

    @Test
    void testGetScore_Success() {
        List<Response> responses = new ArrayList<>();
        Response response = new Response();
        response.setId(1);
        response.setResponse("Java");
        responses.add(response);

        when(questionDao.findById(1)).thenReturn(Optional.of(questions.get(0)));
        ResponseEntity<Integer> scoreResponse=questionService.getScore(responses);

        assertEquals(HttpStatus.OK,scoreResponse.getStatusCode());
        assertEquals(1,scoreResponse.getBody());
    }
}