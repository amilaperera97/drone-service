package com.musalasoft.droneservice.exception.error;

import com.musalasoft.droneservice.dto.response.ResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLTransientConnectionException;
import java.util.Locale;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleHttpMessageNotReadable(@NotNull HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        ResponseDto<Void> responseDto = new ResponseDto<Void>().buildFailureMsg(messageSource.getMessage("malformed.request", new Object[0], Locale.ENGLISH), ex);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Void>> globalExceptionHandler(Exception ex) {
        ResponseDto<Void> responseDto = new ResponseDto<Void>().buildFailureMsg(ex);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Void>> globalNullPointerExceptionHandler(Exception ex) {
        ResponseDto<Void> responseDto = new ResponseDto<Void>().buildFailureMsg(
                messageSource.getMessage("null.pointer.exception", new Object[0], Locale.ENGLISH), ex);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ResponseDto<Void>> dataIntegrityViolationExceptionHandler(Exception ex) {
        final String msg;

        if (ex.getCause().getClass().isAssignableFrom(ConstraintViolationException.class))
            msg = messageSource.getMessage("constraint.violation.exception", new Object[0], Locale.ENGLISH);
        else
            msg = messageSource.getMessage("data.integrity.violation.exception", new Object[0], Locale.ENGLISH);

        return new ResponseEntity<>(
                new ResponseDto<Void>().buildFailureMsg(msg, ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseDto<Void>> handleMaxSizeException(MaxUploadSizeExceededException ex) {

        ResponseDto<Void> responseDto = new ResponseDto<Void>().buildFailureMsg(
                messageSource.getMessage("file.too.large", new Object[0], Locale.ENGLISH), ex);
        return new ResponseEntity<>(responseDto, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({JDBCConnectionException.class, SQLTransientConnectionException.class, CannotCreateTransactionException.class})
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    public ResponseEntity<ResponseDto<Void>> dbConnectionFailHandler(Exception ex) {
        ResponseDto<Void> responseDto = new ResponseDto<Void>().buildFailureMsg(
                messageSource.getMessage("db.connection.failed", new Object[0], Locale.ENGLISH), ex);
        return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
