package csapat.DrivingLicenseAppAPI.dto;

public record UserUpdate(
        String firstName,
        String lastName,
        String email,
        String phone,
        String birthDate,
        String gender,
        Long educationId
) {
}
