package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "students")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    //Kapcsolatok
    @ManyToOne(cascade = {})
    @JoinColumn(name = "school_id")
    private School studentSchool;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "instructor_id")
    private Instructors studentInstructor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User studentUser;

    @OneToMany(
            mappedBy = "reviewAuthor",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Review> reviewList;

    @OneToMany(
            mappedBy = "requestedStudent",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<Request> requestList;

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", studentSchool=" + studentSchool +
                ", studentInstructor=" + studentInstructor +
                ", studentUser=" + studentUser +
                ", reviewList=" + reviewList +
                ", requestList=" + requestList +
                '}';
    }
}
