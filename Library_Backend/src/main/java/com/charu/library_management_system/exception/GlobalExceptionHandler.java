package com.charu.library_management_system.exception;

import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ApiResponse> handleGenreNotFound(GenreNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(ParentGenreNotFoundException.class)
    public ResponseEntity<ApiResponse> handleParentGenreNotFound(ParentGenreNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBookNotFound(BookNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ApiResponse> handleDuplicateIsbn(DuplicateIsbnException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
}
