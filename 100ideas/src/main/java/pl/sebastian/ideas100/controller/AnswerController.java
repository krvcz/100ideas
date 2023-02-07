package pl.sebastian.ideas100.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.domain.model.Answer;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.service.AnswerService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("answers")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping
    public ResponseEntity<List<Answer>> showAnswers() {

        return new ResponseEntity<>(answerService.getAnswers(), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable("id") UUID id) {
        Optional<Answer> answer = answerService.getAnswer(id);

        return answer.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseThrow(NoContentException::new);

    }

    @PostMapping("{id}")
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer) {
        Answer answerAdded = answerService.addAnswer(answer);

        return new ResponseEntity<>(answerAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<Answer> addAnswer(@PathVariable("id") UUID id, @RequestBody Answer answer) {
        Answer answerFound = answerService.updateAnswer(id, answer);

        return new ResponseEntity<>(answerFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeAnswer(@PathVariable("id") UUID id) {
        answerService.removeAnswer(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
