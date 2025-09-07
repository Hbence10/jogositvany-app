package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "reserved_hour")
public class ReservedHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start")
    @Size(max = 2)
    @NotNull
    private int start;

    @Column(name = "end")
    @Size(max = 2)
    @NotNull
    private int end;

//    private ReservedDate date;
}
