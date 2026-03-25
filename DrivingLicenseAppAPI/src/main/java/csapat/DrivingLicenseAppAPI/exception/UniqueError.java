package csapat.DrivingLicenseAppAPI.exception;

public class UniqueError extends RuntimeException {
    public UniqueError(String message) {
        super(message);
    }
}
