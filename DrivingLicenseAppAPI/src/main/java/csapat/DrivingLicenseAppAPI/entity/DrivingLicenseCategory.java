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
@Table(name = "driving_license_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLicenseCategory", procedureName = "getAllDrivingLicenseCategory", resultClasses = DrivingLicenseCategory.class),
        @NamedStoredProcedureQuery(name = "getDrivingLicenseCategory", procedureName = "getDrivingLicenseCategory", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLicenseCategory.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLicenseCategory", procedureName = "deleteDrivingLicenseCategory", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Integer.class, mode = ParameterMode.IN)
        }, resultClasses = String.class)
})
public class DrivingLicenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "min_age")
    @NotNull
    @Size(max = 2)
    private Integer minAge;


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
            mappedBy = "joinRequestCategory",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "school_category",
            joinColumns = @JoinColumn(name = "school_id"),
            inverseJoinColumns = @JoinColumn(name = "driving_license_category_id")
    )
    @JsonIgnore
    private List<School> schoolList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {})
    @JoinTable(
            name = "instructor_category",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "driving_license_category_id")
    )
    @JsonIgnore
    private List<Instructors> instructorsList;

    @OneToMany(mappedBy = "selectedCategory", fetch = FetchType.LAZY, cascade = {})
    @JsonIgnore
    private List<Students> studentsList;


    @JsonIgnore
    @OneToMany(mappedBy = "licenseCategory")
    private List<SchoolCategory> licenseCategory;

    //Constructorok:
    public DrivingLicenseCategory(String name, int minAge) {
        this.name = name;
        this.minAge = minAge;
    }
}
