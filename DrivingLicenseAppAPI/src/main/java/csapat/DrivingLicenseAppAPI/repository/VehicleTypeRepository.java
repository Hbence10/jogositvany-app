package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

    @Procedure(name = "getAllVehichleType", procedureName = "getAllVehichleType")
    List<VehicleType> getAllVehicleType();

    @Procedure(name = "getVehicleType", procedureName = "getVehicleType")
    VehicleType getVehicleType(@Param("idIN") Integer id);

    @Procedure(name = "deleteVehicleType", procedureName = "deleteVehicleType")
    String deleteVehicleType(@Param("idIN") Integer id);
}
