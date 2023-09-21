package study.neo.gateway.exception;

import feign.FeignException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptHandler {
    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandler(FeignException.FeignClientException feignClientException) {
        return getErrorResponse(feignClientException);
    }

    @ExceptionHandler(FeignException.Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictExceptionHandler(FeignException.FeignClientException feignClientException) {
        return getErrorResponse(feignClientException);
    }

    private ErrorResponse getErrorResponse(FeignException.FeignClientException feignClientException) {
        String message = feignClientException.getMessage();
        Pattern pattern = Pattern.compile("\"error\":\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(message);
        log.warn(message);
        return matcher.find() ? new ErrorResponse(matcher.group(1)) : new ErrorResponse(message) ;
    }

    @Data
    @RequiredArgsConstructor
    private static class ErrorResponse {
        private final String error;
        private String description;
    }
}
