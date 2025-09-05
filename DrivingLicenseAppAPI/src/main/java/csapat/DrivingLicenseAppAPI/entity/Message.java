package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "message")
public class Message {

    private int id;
    private String content;
    private LocalDate createdAt;

    private Users messageTo;
    private Users messageFrom;
}
