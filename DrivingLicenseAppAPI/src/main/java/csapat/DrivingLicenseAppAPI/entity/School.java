package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "school")
public class School {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String country;
    private String town;
    private String address;
    private String promoText;
    private String bannerImgPath;

    private Users administrator;
    private List<Instructors> instructorsList;
    private List<Students> studentsList;
}
