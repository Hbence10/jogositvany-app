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
@Table(name = "school_join_request")
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllSchoolJoinRequest", procedureName = "getAllSchoolJoinRequest", resultClasses = SchoolJoinRequest.class),
        @NamedStoredProcedureQuery(name = "getSchoolJoinRequest", procedureName = "getSchoolJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = SchoolJoinRequest.class),
        @NamedStoredProcedureQuery(name = "deleteSchoolJoinRequest", procedureName = "deleteSchoolJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class SchoolJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted;

    @Column(name = "accepted_at")
    @Null
    private Date acceptedAt;

    @Column(name = "sent_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentAt;

    @Column(name = "is_deleted")
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"instructor", "student", "adminSchool", "ownedSchool"})
    private Users schoolJoinRequestUser;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolJoinRequestSchool;

    @ManyToOne
    @JoinColumn(name = "driving_license_category_id")
    @JsonIgnore
    private DrivingLicenseCategory joinRequestCategory;

    public SchoolJoinRequest(Users user, School school, DrivingLicenseCategory joinRequestCategory) {
        this.schoolJoinRequestUser = user;
        this.schoolJoinRequestSchool = school;
        this.joinRequestCategory = joinRequestCategory;
    }

    public SchoolJoinRequest(Users user, School school) {
        this.schoolJoinRequestUser = user;
        this.schoolJoinRequestSchool = school;
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

    public Users getSchoolJoinRequestUser() {
        return schoolJoinRequestUser;
    }

    public void setSchoolJoinRequestUser(Users schoolJoinRequestUser) {
        this.schoolJoinRequestUser = schoolJoinRequestUser;
    }

    public School getSchoolJoinRequestSchool() {
        return schoolJoinRequestSchool;
    }

    public void setSchoolJoinRequestSchool(School schoolJoinRequestSchool) {
        this.schoolJoinRequestSchool = schoolJoinRequestSchool;
    }

    public DrivingLicenseCategory getJoinRequestCategory() {
        return joinRequestCategory;
    }

    public void setJoinRequestCategory(DrivingLicenseCategory joinRequestCategory) {
        this.joinRequestCategory = joinRequestCategory;
    }
}
