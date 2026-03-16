package csapat.DrivingLicenseAppAPI.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        System.out.println(ex.getMessage());
        if (ex.getMessage().contains("for key 'phone'")) {
            return ResponseEntity.status(409).body(Map.of("statusText", "duplicatePhone"));
        } else if (ex.getMessage().contains("for key 'email'")) {
            return ResponseEntity.status(409).body(Map.of("statusText", "duplicateEmail"));
        } else if (ex.getMessage().contains("for key 'license_plate'")) {
            return ResponseEntity.status(409).body(Map.of("statusText", "duplicateLicensePlate"));
        } else if (ex.getMessage().contains("for key 'name'")) {
            return ResponseEntity.status(409).body(Map.of("statusText", "duplicateName"));
        }


        return ResponseEntity.internalServerError().build();
    }
}
