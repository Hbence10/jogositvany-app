package csapat.DrivingLicenseAppAPI.exception;

public class UniqueErrorException extends RuntimeException {
    public UniqueErrorException(String message) {
        super(message);
    }
}
