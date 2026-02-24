package csapat.DrivingLicenseAppAPI.dto;

public record DrivingLessonCard(
        String date,
        String startHour,
        String endHour,
        String town,
        Integer km
) {
}
