package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reserved_date")
@Getter
@Setter
@NoArgsConstructor
public class ReservedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @NotNull
    private Date date;

    @Column(name = "is_full")
    @NotNull
    private boolean isFull = false;

    //Kapcsolatok:
    @OneToMany(
            mappedBy = "reservedDate",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<ReservedHour> reservedHourList;

    //Constructorok:
    public ReservedDate(Date date, boolean isFull) {
        this.date = date;
        this.isFull = isFull;
    }

    @Override
    public String toString() {
        return "ReservedDate{" +
                "id=" + id +
                ", date=" + date +
                ", isFull=" + isFull +
                ", reservedHourList=" + reservedHourList +
                '}';
    }
}
