package csapat.DrivingLicenseAppAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class HourCard {

    private Date startTime;
    private Date endTime;
    private String name;
    private Long drivingLessonId;
    private Date date;

    public HourCard(Date startTime, Date endTime, String name, Long drivingLessonId, Date date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.drivingLessonId = drivingLessonId;
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDrivingLessonId() {
        return drivingLessonId;
    }

    public void setDrivingLessonId(Long drivingLessonId) {
        this.drivingLessonId = drivingLessonId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
