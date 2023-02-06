package pl.sebastian.ideas100.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class QuestionException extends ResponseStatusExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<Object> getNullPointExceptionInResponse(NullPointerException nullPointerException){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", nullPointerException.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



}
