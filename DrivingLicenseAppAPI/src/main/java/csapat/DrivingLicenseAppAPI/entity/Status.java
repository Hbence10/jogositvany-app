package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "status")
public class Status {

    private int id;
    private String name;

    private List<DrivingLessons> drivingLessonsList;
    private List<Request> requestList;
}
