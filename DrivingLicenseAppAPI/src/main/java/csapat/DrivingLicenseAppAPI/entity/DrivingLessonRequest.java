package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;

@Entity
@Table(name = "driving_lesson_request")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLessonRequest", procedureName = "getAllDrivingLessonRequest", resultClasses = DrivingLessonRequest.class),
        @NamedStoredProcedureQuery(name = "getDrivingLessonRequest", procedureName = "getDrivingLessonRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLessonRequest.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLessonRequest", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class DrivingLessonRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "msg")
    @NotNull
    private String msg;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "start_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @NotNull
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted;

    @Column(name = "accepted_at")
    @Null
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedAt;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok:
    @ManyToOne()
    @JoinColumn(name = "student_id")
    private Students dLessonRequestStudent;

    @ManyToOne()
    @JoinColumn(name = "instructor_id")
    private Instructors dLessonInstructor;

    public DrivingLessonRequest(String msg, Date date, Date startTime, Date endTime, Students dLessonRequestStudent, Instructors dLessonInstructor) {
        this.msg = msg;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dLessonRequestStudent = dLessonRequestStudent;
        this.dLessonInstructor = dLessonInstructor;
    }
}
