package com.charu.library_management_system.exception;

import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ApiResponse> handleUserExists(UserExistsException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(ResetTokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleResetTokenExpired(ResetTokenExpiredException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.GONE).body(apiResponse);
    }
    @ExceptionHandler(ResetTokenNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResetTokenNotFound(ResetTokenNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiResponse> handleEmailSending(EmailSendingException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentials(
            BadCredentialsException e) {

        ApiResponse apiResponse = ApiResponse.builder()
                .message(e.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }
    @ExceptionHandler(SubscriptionPlanAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleSubscriptionPlanAlreadyExists(SubscriptionPlanAlreadyExistsException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(SubscriptionPlanNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSubscriptionPlanNotFound(SubscriptionPlanNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(true).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
