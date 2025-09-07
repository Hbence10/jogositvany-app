package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_date")
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date date;

    @Column(name = "is_full")
    @NotNull
    private boolean isFull = false;

    private List<ReservedHour> reservedHourList;
}
