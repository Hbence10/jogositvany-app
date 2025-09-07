package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

@Table(name = "students")
@Entity
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

//    private School school;
//    private Instructors instructors;
//    private Users users;
}
