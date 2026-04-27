package csapat.DrivingLicenseAppAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.Date;

@Table(name = "vehicle")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "getAllVehicle", procedureName = "getAllVehicle", resultClasses = Vehicle.class),
        @NamedStoredProcedureQuery(name = "getVehicle", procedureName = "getVehicle", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        }, resultClasses = Vehicle.class),
        @NamedStoredProcedureQuery(name = "deleteVehicle", procedureName = "deleteVehicle", parameters = {
                @StoredProcedureParameter(name = "idIN", type = Long.class, mode = ParameterMode.IN)
        })
})
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "license_plate")
    @Size(max = 9)
    @Null
    private String licensePlate;

    @Column(name = "name")
    @Null
    @Size(max = 100)
    private String name;

    @Column(name = "is_deleted")
    @NotNull
    @JsonIgnore
    private Boolean isDeleted = false;

    @Column(name = "deleted_at")
    @Null
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    //Kapcsolatok
    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnoreProperties({"vehicleList"})
    @Null
    private VehicleType vehicleType;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    @JsonIgnoreProperties({"vehicles"})
    @Null
    private FuelType fuelType;

    @OneToOne(mappedBy = "vehicle")
    private Instructors instructor;

    //Constructorok
    public Vehicle(String licensePlate, String name) {
        this.licensePlate = licensePlate;
        this.name = name;
    }

    public Vehicle(String licensePlate, String name, VehicleType vehicleType, FuelType fuelType) {
        this.licensePlate = licensePlate;
        this.name = name;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.isDeleted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Instructors getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructors instructor) {
        this.instructor = instructor;
    }
}
