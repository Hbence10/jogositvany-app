package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate createdAt;

    @Column(name = "requested_date")
    @NotNull
    private LocalDateTime requestedDate;

    @Column(name = "msg")
    @NotNull
    private String message;

    @Column(name = "is_exam")
    @NotNull
    private boolean isExam = false;

    private Users sender;
    private Users picker;
    private Status status;
    private Users student;
}
