package csapat.DrivingLicenseAppAPI.service.other;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class HourCard {

    private Date startTime;
    private Date endTime;
    private String name;
    private Integer drivingLessonId;
}
