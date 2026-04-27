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
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLessonRequest.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLessonRequest", procedureName = "", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class DrivingLessonRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Students dLessonRequestStudent;

    @ManyToOne
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Date getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(Date acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Students getdLessonRequestStudent() {
        return dLessonRequestStudent;
    }

    public void setdLessonRequestStudent(Students dLessonRequestStudent) {
        this.dLessonRequestStudent = dLessonRequestStudent;
    }

    public Instructors getdLessonInstructor() {
        return dLessonInstructor;
    }

    public void setdLessonInstructor(Instructors dLessonInstructor) {
        this.dLessonInstructor = dLessonInstructor;
    }
}
