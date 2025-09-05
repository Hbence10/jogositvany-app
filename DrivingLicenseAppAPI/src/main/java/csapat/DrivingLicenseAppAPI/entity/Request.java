package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "request")
public class Request {

    private int id;
    private LocalDate createdAt;
    private LocalDate requestedDate;
    private String message;
    private boolean isExam = false;

    private Users sender;
    private Users picker;
    private Status status;
    private Users student;
}
