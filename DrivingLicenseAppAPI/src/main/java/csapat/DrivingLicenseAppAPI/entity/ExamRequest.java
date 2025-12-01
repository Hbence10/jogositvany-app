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
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "driving_lesson_request")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllExamRequest", procedureName = "getAllExamRequest", resultClasses = ExamRequest.class),
        @NamedStoredProcedureQuery(name = "getExamRequest", procedureName = "getExamRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = ExamRequest.class),
        @NamedStoredProcedureQuery(name = "deleteExamRequest", procedureName = "deleteExamRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class ExamRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "requested_date")
    @NotNull
    private Date requestedDate;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    private Instructors examRequesterInstructor;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({})
    private School examSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({})
    private Students examStudent;
}
