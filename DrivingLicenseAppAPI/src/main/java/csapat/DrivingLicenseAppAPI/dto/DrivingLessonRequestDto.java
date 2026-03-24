package csapat.DrivingLicenseAppAPI.dto;

import java.util.Date;

public record DrivingLessonRequestDto(
        String msg,
        Date date,
        Date startTime,
        Date endTime,
        Long studentId,
        Long instructorId
) {
}
