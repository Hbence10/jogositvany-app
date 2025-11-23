package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ReservedHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservedHourRepository extends JpaRepository<ReservedHour, Integer> {

    @Procedure(name = "getAllReservedHour", procedureName = "getAllReservedHour")
    List<ReservedHour> getAllReservedHour();

    @Procedure(name = "getReservedHour", procedureName = "getReservedHour")
    ReservedHour getReservedHour(@Param("idIN") Integer id);

    @Procedure(name = "deleteReservedHour", procedureName = "deleteReservedHour")
    String deleteReservedHour(@Param("idIN") Integer id);
}
