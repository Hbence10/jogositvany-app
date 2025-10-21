package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
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
    private Integer id;

    @Column(name = "is_accepted")
    private boolean isAccepted = false;

    @Column(name = "accepted_at")
    @Null
    private LocalDateTime acceptedAt;

    @Column(name = "sended_at")
    @Temporal(TemporalType.DATE)
    private Date sendedAt;

    @Column(name = "is_deleted")
    @NotNull
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //kapcsolatok:
    Students instructorRequestStudent;
    Instructors instructorsRequestInstructor;
}
