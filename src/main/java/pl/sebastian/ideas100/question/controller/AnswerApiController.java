package pl.sebastian.ideas100.question.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sebastian.ideas100.question.dto.AnswerStatDto;
import pl.sebastian.ideas100.question.model.Answer;
import pl.sebastian.ideas100.exception.NoContentException;
import pl.sebastian.ideas100.question.service.AnswerService;
import pl.sebastian.ideas100.question.service.AnswerStatMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerApiController {

    private final AnswerService answerService;

    @GetMapping
    public ResponseEntity<List<AnswerStatDto>> showAnswers() {

        return new ResponseEntity<>(answerService.getAnswers(), HttpStatus.OK);

    }

    @GetMapping("{id}")
    public ResponseEntity<AnswerStatDto> getAnswerById(@PathVariable("id") UUID id) {
        AnswerStatDto answer = answerService.getAnswer(id);

        return new ResponseEntity<>(answer, HttpStatus.OK);

    }

    @PostMapping("{id}")
    public ResponseEntity<AnswerStatDto> createAnswer(@PathVariable("id") UUID id, @RequestBody AnswerStatDto answer) {
        AnswerStatDto answerAdded = answerService.addAnswer(id, answer);

        return new ResponseEntity<>(answerAdded, HttpStatus.CREATED);

    }

    @PutMapping("{id}")
    public ResponseEntity<AnswerStatDto> addAnswer(@PathVariable("id") UUID id, @RequestBody AnswerStatDto answer) {
        AnswerStatDto answerFound = answerService.updateAnswer(id, answer);

        return new ResponseEntity<>(answerFound, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> removeAnswer(@PathVariable("id") UUID id) {
        answerService.removeAnswer(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
