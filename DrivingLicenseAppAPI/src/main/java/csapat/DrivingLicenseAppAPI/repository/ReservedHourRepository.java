package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ReservedHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservedHourRepository extends JpaRepository<ReservedHour, Long> {

    @Procedure(name = "getAllReservedHour", procedureName = "getAllReservedHour")
    List<ReservedHour> getAllReservedHour();

    @Procedure(name = "getReservedHour", procedureName = "getReservedHour")
    Optional<ReservedHour> getReservedHour(@Param("idIN") Long id);

    @Procedure(name = "deleteReservedHour", procedureName = "deleteReservedHour")
    void deleteReservedHour(@Param("idIN") Long id);

    @Procedure(name = "getReservedHourIdByDateAndInstructor", procedureName = "getReservedHourIdByDateAndInstructor")
    List<Long> getReservedHourIdByDateAndInstructor(@Param("dateIN") LocalDate date, @Param("instructorIdIN") Long instructorId);

    @Procedure(name = "getReservedHoursBetweenTwoDate", procedureName = "getReservedHoursBetweenTwoDate")
    List<Long> getReservedHoursBetweenTwoDate(@Param("instructorIdIN") Long instructorId, @Param("startDateIN") Date startDate, @Param("endDateIN") Date endDate);
}
