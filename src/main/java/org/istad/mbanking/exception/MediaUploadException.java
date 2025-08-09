package org.istad.mbanking.exception;

import org.istad.mbanking.base.BaseError;
import org.istad.mbanking.base.BaseErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class MediaUploadException {

    @Value("${spring.servlet.multipart.max-request-size}")
    private String requestMaxSize;

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseErrorResponse maxUploadSizeException(MaxUploadSizeExceededException e) {
        BaseError<String> error = BaseError.<String>builder()
                .code(e.getStatusCode().toString())
                .description("Size of the file is too large. Max allowed size is " + requestMaxSize)
                .build();
        return new BaseErrorResponse(error);
    }
}
