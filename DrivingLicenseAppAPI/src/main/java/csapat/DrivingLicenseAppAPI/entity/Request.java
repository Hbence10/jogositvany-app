package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "request")
@Getter
@Setter
@NoArgsConstructor
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
    @ManyToOne(cascade = {})
    @JoinColumn(name = "sender_id")
    private User senderUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "picker_id")
    private User pickerUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    private Status requestStatus;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "student_id")
    private Students requestedStudent;

    //Constructorok:
    public Request(LocalDate createdAt, LocalDateTime requestedDate, String message, boolean isExam) {
        this.createdAt = createdAt;
        this.requestedDate = requestedDate;
        this.message = message;
        this.isExam = isExam;
    }
}
