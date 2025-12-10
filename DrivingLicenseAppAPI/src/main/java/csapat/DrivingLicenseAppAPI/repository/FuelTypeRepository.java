package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuelTypeRepository extends JpaRepository<FuelType, Integer> {

    @Procedure(name = "getAllFuelType", procedureName = "getAllFuelType")
    List<FuelType> getAllFuelType();

    @Procedure(name = "getFuelType", procedureName = "getFuelType")
    Optional<FuelType> getFuelType(@Param("idIN") Integer id);

    @Procedure(name = "deleteFuelType", procedureName = "deleteFuelType")
    String deleteFuelType(@Param("idIN") Integer id);
}
