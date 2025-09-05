package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reserved_date")
public class ReservedDate {

    private int id;
    private LocalDate date;
    private boolean isFull = false;

    private List<ReservedHour> reservedHourList;
}
