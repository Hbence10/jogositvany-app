package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Null;
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
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = InstructorJoinRequest.class),
        @NamedStoredProcedureQuery(name = "deleteInstructorJoinRequest", procedureName = "deleteInstructorJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class InstructorJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted;

    @Column(name = "accepted_at")
    @Null
    @Temporal(TemporalType.TIMESTAMP)
    private Date acceptedAt;

    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    @Column(name = "is_deleted")
    @Null
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties({"studentSchool", "studentInstructor"})
    private Students instructorJoinRequestStudent;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonIgnore
    private Instructors instructorJoinRequestInstructor;

    public InstructorJoinRequest(Students instructorJoinRequestStudent, Instructors instructorJoinRequestInstructor) {
        this.instructorJoinRequestStudent = instructorJoinRequestStudent;
        this.instructorJoinRequestInstructor = instructorJoinRequestInstructor;
    }

    public InstructorJoinRequest(Students instructorJoinRequestStudent, Instructors instructorJoinRequestInstructor, Boolean isAccepted) {
        this.instructorJoinRequestStudent = instructorJoinRequestStudent;
        this.instructorJoinRequestInstructor = instructorJoinRequestInstructor;
        this.isAccepted = isAccepted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
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

    public Students getInstructorJoinRequestStudent() {
        return instructorJoinRequestStudent;
    }

    public void setInstructorJoinRequestStudent(Students instructorJoinRequestStudent) {
        this.instructorJoinRequestStudent = instructorJoinRequestStudent;
    }

    public Instructors getInstructorJoinRequestInstructor() {
        return instructorJoinRequestInstructor;
    }

    public void setInstructorJoinRequestInstructor(Instructors instructorJoinRequestInstructor) {
        this.instructorJoinRequestInstructor = instructorJoinRequestInstructor;
    }
}
