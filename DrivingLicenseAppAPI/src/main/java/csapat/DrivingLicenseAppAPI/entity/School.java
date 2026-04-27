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
@Table(name = "school")
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllSchool", procedureName = "getAllSchool", resultClasses = School.class),
        @NamedStoredProcedureQuery(name = "getSchool", procedureName = "getSchool", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = School.class),
        @NamedStoredProcedureQuery(name = "deleteSchool", procedureName = "deleteSchool", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }),
        @NamedStoredProcedureQuery(name = "getSchoolBySearch", procedureName = "getSchoolBySearch", parameters = {
                @StoredProcedureParameter(name = "townnameIN", type = String.class, mode = ParameterMode.IN),
        }, resultClasses = Long.class)
})
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "email", unique = true)
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "phone", unique = true)
    @NotNull
    @Size(max = 100)
    private String phone;

    @Column(name = "country")
    @NotNull
    @Size(max = 100)
    private String country;

    @Column(name = "town")
    @NotNull
    @Size(max = 100)
    private String town;

    @Column(name = "address")
    @NotNull
    @Size(max = 100)
    private String address;

    @Column(name = "promo_text")
    @NotNull
    private String promoText;

    @Column(name = "banner_img_path")
    @NotNull
    private String bannerImgPath = "";

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
    @OneToOne()
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"ownedSchool", "role", "instructor", "student", "adminSchool", "userEducation"})
    private Users owner;

    @OneToMany(mappedBy = "adminSchool")
    @JsonIgnore
    @Null
    private List<Users> adminList;

    @OneToMany(
            mappedBy = "instructorSchool",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"instructorSchool", "vehicle", "reviewList", "students", "drivingLessonRequestList", "examRequestList", "instructorDrivingLessons", "instructorJoinRequestList"})
    private List<Instructors> instructorsList;

    @OneToMany(
            mappedBy = "schoolOpeningDetail",
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"schoolOpeningDetail"})
    private List<OpeningDetails> openingDetails;

    @OneToMany(
            mappedBy = "aboutSchool",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Review> reviewList;

    @OneToMany(mappedBy = "studentSchool", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"studentSchool", "studentInstructor"})
    private List<Students> studentsList;

    @OneToMany(mappedBy = "schoolJoinRequestSchool", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SchoolJoinRequest> schoolJoinRequestList;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "schoolCategory")
    private List<SchoolCategory> licenseCategoryList;

    public School(String name, String email, String phone, String country, String town, String address, String promoText, Users owner) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.town = town;
        this.address = address;
        this.promoText = promoText;
        this.owner = owner;
        this.bannerImgPath = "http://localhost:8080/coverImages/defaultCoverImg.jpg";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPromoText() {
        return promoText;
    }

    public void setPromoText(String promoText) {
        this.promoText = promoText;
    }

    public String getBannerImgPath() {
        return bannerImgPath;
    }

    public void setBannerImgPath(String bannerImgPath) {
        this.bannerImgPath = bannerImgPath;
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

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public List<Users> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<Users> adminList) {
        this.adminList = adminList;
    }

    public List<Instructors> getInstructorsList() {
        return instructorsList;
    }

    public void setInstructorsList(List<Instructors> instructorsList) {
        this.instructorsList = instructorsList;
    }

    public List<OpeningDetails> getOpeningDetails() {
        return openingDetails;
    }

    public void setOpeningDetails(List<OpeningDetails> openingDetails) {
        this.openingDetails = openingDetails;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public List<Students> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Students> studentsList) {
        this.studentsList = studentsList;
    }

    public List<SchoolJoinRequest> getSchoolJoinRequestList() {
        return schoolJoinRequestList;
    }

    public void setSchoolJoinRequestList(List<SchoolJoinRequest> schoolJoinRequestList) {
        this.schoolJoinRequestList = schoolJoinRequestList;
    }

    public List<SchoolCategory> getLicenseCategoryList() {
        return licenseCategoryList;
    }

    public void setLicenseCategoryList(List<SchoolCategory> licenseCategoryList) {
        this.licenseCategoryList = licenseCategoryList;
    }
}
