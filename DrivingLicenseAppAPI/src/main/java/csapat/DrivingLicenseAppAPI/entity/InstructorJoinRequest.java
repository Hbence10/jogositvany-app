package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllInstructorJoinRequest", procedureName = "getAllInstructorJoinRequest", resultClasses = InstructorJoinRequest.class),
        @NamedStoredProcedureQuery(name = "getInstructorJoinRequest", procedureName = "getInstructorJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = InstructorJoinRequest.class),
        @NamedStoredProcedureQuery(name = "deleteInstructorJoinRequest", procedureName = "deleteInstructorJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class InstructorJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted = false;

    @Column(name = "accepted_at")
    @Null
    private LocalDateTime acceptedAt;

    @Column(name = "sended_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendedAt;

    @Column(name = "is_deleted")
    @Null
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
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
