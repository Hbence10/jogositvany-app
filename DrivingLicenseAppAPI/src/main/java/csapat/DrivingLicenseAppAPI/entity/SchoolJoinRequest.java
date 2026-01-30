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
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllSchoolJoinRequest", procedureName = "getAllSchoolJoinRequest", resultClasses = SchoolJoinRequest.class),
        @NamedStoredProcedureQuery(name = "getSchoolJoinRequest", procedureName = "getSchoolJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = SchoolJoinRequest.class),
        @NamedStoredProcedureQuery(name = "deleteSchoolJoinRequest", procedureName = "deleteSchoolJoinRequest", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class SchoolJoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted;

    @Column(name = "accepted_at")
    @Null
    private Date acceptedAt;

    @Column(name = "sended_at")
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
    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"instructor", "student", "adminSchool", "ownedSchool"})
    private Users schoolJoinRequestUser;

    @ManyToOne()
    @JoinColumn(name = "school_id")
    @JsonIgnore
    private School schoolJoinRequestSchool;

    @ManyToOne()
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
}
