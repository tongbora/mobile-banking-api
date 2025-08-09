package org.istad.mbanking.exception;

import org.istad.mbanking.base.BaseError;
import org.istad.mbanking.base.BaseErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MethodNotValidException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BaseErrorResponse handleMethodNotValid(MethodArgumentNotValidException e){
        BaseError<List<?>> baseError = new BaseError<>();
        List<Map<String, Object>> errors = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, Object> errorMap = Map.of(
                    "field", error.getField(),
                    "message", error.getDefaultMessage()
            );
            errors.add(errorMap);
        });
        baseError.setCode(HttpStatus.BAD_REQUEST.getReasonPhrase());
        baseError.setDescription(errors);
        return new BaseErrorResponse(baseError);
    }
}
