package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
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

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "start_time")
    @NotNull
    @Size(max = 2)
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @NotNull
    @Size(max = 2)
    @Temporal(TemporalType.TIME)
    private Date endTime;

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
    @ManyToOne(cascade = {})
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({})
    private Students dLessonRequestStudent;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    @JsonIgnoreProperties({})
    private Instructors dLessonInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "status_id")
    @JsonIgnoreProperties({})
    private Status dLessonStatus;

}
