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
import java.util.List;

@Entity
@Table(name = "driving_lesson_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLessonType", procedureName = "getAllDrivingLessonType", resultClasses = DrivingLessonType.class),
        @NamedStoredProcedureQuery(name = "getDrivingLessonType", procedureName = "getDrivingLessonType", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = Users.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLessonType", procedureName = "deleteDrivingLessonType", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class DrivingLessonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "price")
    @NotNull
    @Size(max = 5)
    private Integer price;

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
    @OneToMany(
            mappedBy = "drivingLessonType",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<DrivingLessons> instructorDrivingLessons;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "license_category_id")
    @JsonIgnoreProperties({})
    private DrivingLicenseCategory drivingLicenseCategory;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({})
    private School drivingTypeSchool;

    @OneToMany(mappedBy = "dLessonRequestType", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<DrivingLessonRequest> drivingLessonRequestList;
}
