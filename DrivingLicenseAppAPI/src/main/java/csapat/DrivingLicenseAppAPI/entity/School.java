package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "school")
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

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

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "administrator_id")
    private Users administrator;

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

    //Constructorok

    public School() {
    }

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

    //Getterek & Setterek
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", town='" + town + '\'' +
                ", address='" + address + '\'' +
                ", promoText='" + promoText + '\'' +
                ", bannerImgPath='" + bannerImgPath + '\'' +
                ", administrator=" + administrator +
                ", instructorsList=" + instructorsList +
                ", openingDetails=" + openingDetails +
                ", reviewList=" + reviewList +
                ", studentsList=" + studentsList +
                '}';
    }
}
