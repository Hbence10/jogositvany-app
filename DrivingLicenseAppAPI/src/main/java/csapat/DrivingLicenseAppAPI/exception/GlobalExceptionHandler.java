package csapat.DrivingLicenseAppAPI.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UniqueError.class)
    public ResponseEntity<Object> handleUniqueError(UniqueError ex) {
        return ResponseEntity.status(409).body(ex.getMessage());
    }

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<Object> handleNotFoundError(NotFound ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidData.class)
    public ResponseEntity<Object> handleInvalidData(InvalidData ex) {
        return ResponseEntity.status(415).body(ex.getMessage());
    }
}
