package net.daneshkar.authorssystem.exception;

import net.daneshkar.authorssystem.dto.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthorNotFoundException.class, ArticleNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<BaseResponse<Void>> handleNotFound(Exception e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({DuplicateArticleException.class, InvalidUserNameException.class, WeakPasswordException.class,
            IllegalArgumentException.class})
    public ResponseEntity<BaseResponse<Void>> handleBadRequest(Exception e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return build(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<BaseResponse<Void>> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(BaseResponse.failure(status.value(), message));
    }
}
