package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "instructor")
public class Instructors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "promo_text")
    @NotNull
    private String promoText;

    //Kapcsolatok:
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users instructorUser;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    private School instructorSchool;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @OneToMany(
            mappedBy = "aboutInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "studentInstructor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Students> students;

    //Constructorok:
    public Instructors() {
    }

    public Instructors(String promoText) {
        this.promoText = promoText;
    }

    //Getterek & Setterek:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPromoText() {
        return promoText;
    }

    public void setPromoText(String promoText) {
        this.promoText = promoText;
    }

    @Override
    public String toString() {
        return "Instructors{" +
                "id=" + id +
                ", promoText='" + promoText + '\'' +
                ", instructorUser=" + instructorUser +
                ", instructorSchool=" + instructorSchool +
                ", vehicle=" + vehicle +
                ", reviewList=" + reviewList +
                ", students=" + students +
                '}';
    }
}
