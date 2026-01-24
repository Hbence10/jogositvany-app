package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.ReservedDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReservedDateRepository extends JpaRepository<ReservedDate, Integer> {

    @Procedure(name = "getAllReservedDate", procedureName = "getAllReservedDate")
    List<ReservedDate> getAllReservedDate();

    @Procedure(name = "getReservedDate", procedureName = "getReservedDate")
    Optional<ReservedDate> getReservedDate(@Param("idIN") Integer id);

    @Procedure(name = "deleteReservedDate", procedureName = "deleteReservedDate")
    String deleteReservedDate(@Param("idIN") Integer id);

    @Procedure(name = "getReservedDateByDate", procedureName = "getReservedDateByDate")
    Optional<ReservedDate> getReservedDateByDate(@Param("wantedDateIN") Date wantedDate);

    Optional<ReservedDate> findByDate(Date date);
}
