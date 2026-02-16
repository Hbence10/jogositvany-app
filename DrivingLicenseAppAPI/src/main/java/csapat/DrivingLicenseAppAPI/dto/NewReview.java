package csapat.DrivingLicenseAppAPI.dto;

public record NewReview(
        String reviewText,
        Double rating,
        Integer studentId,
        Boolean isAnonymous,
        Integer instructorId,
        Integer schoolId
) {
}
