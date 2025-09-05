package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "peyment_methods")
public class PaymentMethod {

    private int id;
    private String name;

    private List<DrivingLessons> drivingLessonsList;
}
