package pl.sebastian.ideas100.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.domain.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionApiController {

    private final QuestionService questionService;

    public QuestionApiController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<Question>> showQuestions() {

        return new ResponseEntity<>(questionService.getQuestions(), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") UUID id) {
        Optional<Question> question = questionService.getQuestion(id);

        return question.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(NoContentException::new);

    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Question> createQuestion(Question question) {
        Question questionAdded = questionService.addQuestion(question);

        return new ResponseEntity<>(questionAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        UUID id = UUID.randomUUID();
        Question questionFound = questionService.updateQuestion(id, question);

        return new ResponseEntity<>(questionFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeQuestion(@PathVariable("id") UUID id) {
        questionService.removeQuestion(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
