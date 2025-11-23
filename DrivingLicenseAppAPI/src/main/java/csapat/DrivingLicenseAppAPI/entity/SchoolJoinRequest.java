package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
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

    @Column(name = "requested_role")
    @NotNull
    @Size(max = 10)
    private String requestedRole;

    @Column(name = "is_accepted")
    @Null
    private Boolean isAccepted;

    @Column(name = "accepted_at")
    @Null
    private LocalDateTime acceptedAt;

    @Column(name = "sended_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendedAt;

    @Column(name = "is_deleted")
    @Null
    private Boolean isDeleted;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({})
    private User schoolJoinRequestUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"owner", "adminList", "instructorsList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    private School schoolJoinRequestSchool;

    public SchoolJoinRequest(String requestedRole, User user, School school) {
        this.requestedRole = requestedRole;
    }
}
