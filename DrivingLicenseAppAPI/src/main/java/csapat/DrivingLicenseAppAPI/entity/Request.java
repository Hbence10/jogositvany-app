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

    //Kapcsolatok:
//    private Users sender;
//    private Users picker;
    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status requestStatus;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "student_id")
    private Students requestedStudent;

    //Constructorok:
    public Request() {
    }

    public Request(LocalDate createdAt, LocalDateTime requestedDate, String message, boolean isExam) {
        this.createdAt = createdAt;
        this.requestedDate = requestedDate;
        this.message = message;
        this.isExam = isExam;
    }

    //Getterek & Setterek:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isExam() {
        return isExam;
    }

    public void setExam(boolean exam) {
        isExam = exam;
    }
}
