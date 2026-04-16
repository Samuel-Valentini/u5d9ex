package samuelvalentini.u5d9ex.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import samuelvalentini.u5d9ex.dto.ErrorPayload;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPayload handleNotFound(NotFoundException ex) {
        return new ErrorPayload(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(BadRequestException ex) {
        return new ErrorPayload(ex.getMessages(), LocalDateTime.now());
    }

    @ExceptionHandler({HandlerMethodValidationException.class, NumberFormatException.class, MethodArgumentTypeMismatchException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorPayload handleBadRequest(HandlerMethodValidationException ex) {
        List<String> messages = ex.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList();
        return new ErrorPayload(messages, LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorPayload handleGenericException(Exception ex) {
        log.error("Errore interno: ", ex);
        return new ErrorPayload("Errore interno del server", LocalDateTime.now());
    }


}
