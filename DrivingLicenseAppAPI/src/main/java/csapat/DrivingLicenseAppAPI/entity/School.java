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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "school")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "email")
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "phone")
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
    private String bannerImgPath;

    @Column(name = "is_deleted")
    @NotNull
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    private LocalDateTime deletedAt;

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(
            mappedBy = "adminSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<User> adminList;

    @OneToMany(
            mappedBy = "instructorSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Instructors> instructorsList;

    @OneToMany(
            mappedBy = "schoolOpeningDetail",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({"schoolOpeningDetail"})
    private List<OpeningDetails> openingDetails;

    @OneToMany(
            mappedBy = "aboutSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "studentSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Students> studentsList;

    @OneToMany(
            mappedBy = "drivingTypeSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<DrivingLessonType> drivingLessonsType;

    @OneToMany(
            mappedBy = "examSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({})
    private List<ExamRequest> examRequestList;

    @OneToMany(
            mappedBy = "schoolJoinRequestSchool",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JsonIgnoreProperties({"owner", "adminList", "instructorList", "reviewList", "studentsList", "drivingLessonsType", "examRequestList", "schoolJoinRequestList"})
    private List<SchoolJoinRequest> schoolJoinRequestList;

    //Constructorok
    public School(String name, String email, String phone, String country, String town, String address, String promoText, String bannerImgPath) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.town = town;
        this.address = address;
        this.promoText = promoText;
        this.bannerImgPath = bannerImgPath;
    }
}
