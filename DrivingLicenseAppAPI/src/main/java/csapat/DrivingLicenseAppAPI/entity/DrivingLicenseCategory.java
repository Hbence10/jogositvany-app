package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "driving_license_category")
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllDrivingLicenseCategory", procedureName = "getAllDrivingLicenseCategory", resultClasses = DrivingLicenseCategory.class),
        @NamedStoredProcedureQuery(name = "getDrivingLicenseCategory", procedureName = "getDrivingLicenseCategory", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = DrivingLicenseCategory.class),
        @NamedStoredProcedureQuery(name = "deleteDrivingLicenseCategory", procedureName = "deleteDrivingLicenseCategory", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class DrivingLicenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "min_age")
    @NotNull
    @Size(max = 2)
    private Integer minAge;

    //Kapcsolatok:
    @OneToMany(mappedBy = "joinRequestCategory", fetch = FetchType.LAZY

    )
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "school_category", joinColumns = @JoinColumn(name = "school_id"), inverseJoinColumns = @JoinColumn(name = "driving_license_category_id"))
    @JsonIgnore
    private List<School> schoolList;

    @OneToMany(mappedBy = "selectedCategory", fetch = FetchType.LAZY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public List<SchoolJoinRequest> getSchoolJoinRequestList() {
        return schoolJoinRequestList;
    }

    public void setSchoolJoinRequestList(List<SchoolJoinRequest> schoolJoinRequestList) {
        this.schoolJoinRequestList = schoolJoinRequestList;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    public List<Students> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Students> studentsList) {
        this.studentsList = studentsList;
    }

    public List<SchoolCategory> getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(List<SchoolCategory> licenseCategory) {
        this.licenseCategory = licenseCategory;
    }
}
