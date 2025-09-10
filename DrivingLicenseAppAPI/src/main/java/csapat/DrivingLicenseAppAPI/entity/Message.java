package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    @NotNull
    private String content;

    @Column(name = "created_at")
    @NotNull
    private LocalDate createdAt;

    //Kapcsolatok:
//    private Users messageTo;
//    private Users messageFrom;
}
