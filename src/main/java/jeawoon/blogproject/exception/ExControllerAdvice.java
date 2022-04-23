package jeawoon.blogproject.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("jeawoon.blogproject.controller.api")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        return new ErrorResult("EX", "분석필요한 Exception 발생");
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        ErrorResult errorResult = new ErrorResult("USER-EX(runtime)", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST); //사용자의 오류로
    }



}
