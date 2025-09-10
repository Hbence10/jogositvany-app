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
    public ReservedDate() {
    }

    public ReservedDate(Date date, boolean isFull) {
        this.date = date;
        this.isFull = isFull;
    }

    //Getterek & Setterek:
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
