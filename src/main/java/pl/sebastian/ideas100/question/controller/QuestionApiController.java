package pl.sebastian.ideas100.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.question.dto.QuestionStatDto;
import pl.sebastian.ideas100.question.model.Question;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.question.service.QuestionService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionApiController {

    private final QuestionService questionService;

    @GetMapping
    public ResponseEntity<Page<Question>> showQuestions(Pageable pageable) {

        return new ResponseEntity<>(questionService.getQuestions(pageable), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionStatDto> getQuestionById(@PathVariable("id") UUID id) {
        QuestionStatDto question = questionService.getQuestion(id);

        return  new ResponseEntity<>(question, HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity<Question> createQuestion(QuestionStatDto questionStatDto) {
        Question questionAdded = questionService.addQuestion(questionStatDto);

        return new ResponseEntity<>(questionAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionStatDto questionStatDto) {
        UUID id = UUID.randomUUID();
        Question questionFound = questionService.updateQuestion(id, questionStatDto);

        return new ResponseEntity<>(questionFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeQuestion(@PathVariable("id") UUID id) {
        questionService.removeQuestion(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
