package dasturlash.uz.controller;

import dasturlash.uz.exceptions.AppBadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handle(AppBadException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
