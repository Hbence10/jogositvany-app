package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "instructor_join_request")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InstructorJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "is_accepted")
    @Null
    private boolean isAccepted = false;

    @Column(name = "accepted_at")
    @Null
    private LocalDateTime acceptedAt;

    @Column(name = "sended_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendedAt;

    @Column(name = "is_deleted")
    @Null
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({})
    private Students instructorJoinRequestStudent;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({})
    private Instructors instructorJoinRequestInstructor;

    public InstructorJoinRequest(Students instructorJoinRequestStudent, Instructors instructorJoinRequestInstructor) {
        this.instructorJoinRequestStudent = instructorJoinRequestStudent;
        this.instructorJoinRequestInstructor = instructorJoinRequestInstructor;
    }
}
