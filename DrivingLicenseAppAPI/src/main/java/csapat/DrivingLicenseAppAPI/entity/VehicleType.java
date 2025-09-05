package csapat.DrivingLicenseAppAPI.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "vehicle_type")
public class VehicleType {

    private int id;
    private String name;

    private List<Vehicle> vehicleList;
}
