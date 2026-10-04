package com.charu.library_management_system.exception;

import com.charu.library_management_system.dto.responseDTO.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ApiResponse> handleGenreNotFound(GenreNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(ParentGenreNotFoundException.class)
    public ResponseEntity<ApiResponse> handleParentGenreNotFound(ParentGenreNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBookNotFound(BookNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ApiResponse> handleUserExists(UserExistsException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(ResetTokenExpiredException.class)
    public ResponseEntity<ApiResponse> handleResetTokenExpired(ResetTokenExpiredException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.GONE).body(apiResponse);
    }
    @ExceptionHandler(ResetTokenNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResetTokenNotFound(ResetTokenNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiResponse> handleEmailSending(EmailSendingException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

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
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(SubscriptionPlanNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSubscriptionPlanNotFound(SubscriptionPlanNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(SubscriptionAlreadyInactiveException.class)
    public ResponseEntity<ApiResponse> handleSubscriptionAlreadyInactive(SubscriptionAlreadyInactiveException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<ApiResponse> handleSubscriptionNotFound(SubscriptionNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(ActiveSubscriptionNotFoundException.class)
    public ResponseEntity<ApiResponse> handleActiveSubscriptionNotFound(ActiveSubscriptionNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<ApiResponse> handlePaymentNotFound(PaymentNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(BookNotActiveException.class)
    public ResponseEntity<ApiResponse> handleBookNotActive(BookNotActiveException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ApiResponse> handleBookNotAvailable(BookNotAvailableException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookAlreadyBorrowedException.class)
    public ResponseEntity<ApiResponse> handleBookAlreadyBorrowed(BookAlreadyBorrowedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookCheckoutLimitExceededException.class)
    public ResponseEntity<ApiResponse> handleBookCheckoutLimitExceeded(BookCheckoutLimitExceededException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookAlreadyReturnedException.class)
    public ResponseEntity<ApiResponse> handleBookAlreadyReturned(BookAlreadyReturnedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookCannotBeRenewedException.class)
    public ResponseEntity<ApiResponse> handleBookCannotBeRenewed(BookCannotBeRenewedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookLoanNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBookLoanNotFound(BookLoanNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(FineAlreadyPaidException.class)
    public ResponseEntity<ApiResponse> handleFineAlreadyPaid(FineAlreadyPaidException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(FineAlreadyWaivedException.class)
    public ResponseEntity<ApiResponse> handleFineAlreadyWaived(FineAlreadyWaivedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(FineNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFineNotFound(FineNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(BookAlreadyAvailableException.class)
    public ResponseEntity<ApiResponse> handleBookAlreadyAvailable(BookAlreadyAvailableException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(MaxReservationLimitException.class)
    public ResponseEntity<ApiResponse> handleMaxReservationLimit(MaxReservationLimitException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFineNotFound(ReservationNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(ReservationCannotBeCancelledException.class)
    public ResponseEntity<ApiResponse> handleReservationCannotBeCancelled(ReservationCannotBeCancelledException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(UserAlreadyHasReservationException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyHasReservation(UserAlreadyHasReservationException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(UserAlreadyHasBookLoanException.class)
    public ResponseEntity<ApiResponse> handleUserAlreadyHasBookLoan(UserAlreadyHasBookLoanException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage()).status(false).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(BookAlreadyReviewedException.class)
    public ResponseEntity<ApiResponse> handleBookAlreadyReviewed(BookAlreadyReviewedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(BookNotEligibleForReviewException.class)
    public ResponseEntity<ApiResponse> handleBookNotEligibleForReview(BookNotEligibleForReviewException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(BookReviewNotFoundException.class)
    public ResponseEntity<ApiResponse> handleBookReviewNotFound(BookReviewNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(ReviewNotOwnedByCurrentUserException.class)
    public ResponseEntity<ApiResponse> handleReviewNotOwnedByCurrentUser(
            ReviewNotOwnedByCurrentUserException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }
    @ExceptionHandler(BookAlreadyInWishlistException.class)
    public ResponseEntity<ApiResponse> handleBookAlreadyInWishlist(
            BookAlreadyInWishlistException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<ApiResponse> handleWishlistNotFound(
            WishlistNotFoundException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }
    @ExceptionHandler(NoDamagedCopiesException.class)
    public ResponseEntity<ApiResponse> handleNoDamagedCopies(
            NoDamagedCopiesException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(DuplicateIsbnException.class)
    public ResponseEntity<ApiResponse> handleDuplicateIsbn(
            DuplicateIsbnException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(OverdueBookExistsException.class)
    public ResponseEntity<ApiResponse> handleOverdueBookExists(
            OverdueBookExistsException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(InvalidStockUpdateException.class)
    public ResponseEntity<ApiResponse> handleInvalidStockUpdate(
            InvalidStockUpdateException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(LoanPeriodExceededException.class)
    public ResponseEntity<ApiResponse> handleLoanPeriodExceeded(
            LoanPeriodExceededException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler(ReservationCannotBeFulfilledException.class)
    public ResponseEntity<ApiResponse> handleReservationCannotBeFulfilled(
            ReservationCannotBeFulfilledException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(UnpaidFinesExistsException.class)
    public ResponseEntity<ApiResponse> handleUnpaidFinesExists(
            UnpaidFinesExistsException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(DuplicateFineException.class)
    public ResponseEntity<ApiResponse> handleDuplicateFine(
            DuplicateFineException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message(ex.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleUnreadableBody(HttpMessageNotReadableException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Request body is missing or contains invalid JSON or values")
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex)
    {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Validation failed: " + errors)
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse> handleMethodValidation(HandlerMethodValidationException ex)
    {
        String errors = ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Validation failed: " + errors)
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiResponse> handleInvalidSortField(PropertyReferenceException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Invalid sort field: " + ex.getPropertyName())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'")
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParam(MissingServletRequestParameterException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Missing required parameter: " + ex.getParameterName())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException ex)
    {
        ApiResponse apiResponse = ApiResponse.builder()
                .message("You do not have permission to access this resource")
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpected(Exception ex)
    {
        // Spring's own web errors (unknown URL → 404, wrong method → 405, …) keep their real status
        if (ex instanceof ErrorResponse errorResponse) {
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Request could not be processed: " + ex.getMessage())
                    .status(false)
                    .build();
            return ResponseEntity.status(errorResponse.getStatusCode()).body(apiResponse);
        }

        // Anything else is a genuine server-side problem: log the full detail, show a safe message
        log.error("Unexpected error", ex);

        ApiResponse apiResponse = ApiResponse.builder()
                .message("Something went wrong. Please try again later.")
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }
}
