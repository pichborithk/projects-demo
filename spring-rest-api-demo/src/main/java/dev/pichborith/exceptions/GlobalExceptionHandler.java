package dev.pichborith.exceptions;

import dev.pichborith.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto<Object>> handleException(
        BadRequestException e) {

        ResponseDto<Object> response = new ResponseDto<>(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDto<Object>> handleException(
        ConflictException e) {

        ResponseDto<Object> response = new ResponseDto<>(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResponseDto<Object>> handleException(
        UnauthorizedException e) {

        ResponseDto<Object> response = new ResponseDto<>(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e) {

        ResponseDto<Object> response = new ResponseDto<>(e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
