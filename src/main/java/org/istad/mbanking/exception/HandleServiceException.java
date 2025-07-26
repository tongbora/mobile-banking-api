package org.istad.mbanking.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class HandleServiceException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException e) {
        Map<String, Object> body = Map.of(
                "status", e.getStatusCode(),
                "message", e.getReason()
        );
        return new ResponseEntity<>(body, e.getStatusCode());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> maxUploadSizeException(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body(
                Map.of("status", e.getStatusCode(),
                        "message", "File size exceeds limit of 5MB",
                        "error", e.getMessage()
                        )
        );
    }

}
