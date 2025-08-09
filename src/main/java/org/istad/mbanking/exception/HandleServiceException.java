package org.istad.mbanking.exception;

import org.istad.mbanking.base.BaseError;
import org.istad.mbanking.base.BaseErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;


@RestControllerAdvice
public class HandleServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException e) {
        BaseError<String> body = new BaseError();
        body.setCode(e.getStatusCode().toString());
        body.setDescription(e.getReason());
        BaseErrorResponse response = new BaseErrorResponse(body);

        return new ResponseEntity<>(response, e.getStatusCode());
    }

}
