package csapat.DrivingLicenseAppAPI.dto;

import java.util.Date;

public record DrivingLessonRequestDto(
        String msg,
        String date,
        String startTime,
        String endTime,
        Long studentId,
        Long instructorId
) {
}
