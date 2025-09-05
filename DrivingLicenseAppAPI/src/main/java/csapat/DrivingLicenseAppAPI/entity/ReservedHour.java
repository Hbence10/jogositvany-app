package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "reserved_hour")
public class ReservedHour {

    private int id;
    private int start;
    private int end;

    private ReservedDate date;
}
