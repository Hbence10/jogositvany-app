package csapat.DrivingLicenseAppAPI.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UniqueErrorException.class)
    public ResponseEntity<Object> handleUniqueError(UniqueErrorException ex) {
        return ResponseEntity.status(409).body(Map.of("statusText", ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundError(NotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Object> handleInvalidData(InvalidDataException ex) {
        return ResponseEntity.status(415).body(ex.getMessage());
    }
}
