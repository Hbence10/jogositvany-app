package csapat.DrivingLicenseAppAPI.dto;

public record NewReview(
        String reviewText,
        Double rating,
        Long studentId,
        Boolean isAnonymous,
        Long instructorId,
        Long schoolId
) {
}
