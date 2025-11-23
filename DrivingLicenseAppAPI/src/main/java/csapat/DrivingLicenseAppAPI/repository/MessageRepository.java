package csapat.DrivingLicenseAppAPI.repository;

import csapat.DrivingLicenseAppAPI.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Procedure(name = "getAllMessage", procedureName = "getAllMessage")
    List<Message> getAllMessage();

    @Procedure(name = "getMessage", procedureName = "getMessage")
    Message getMessage(@Param("idIN") Integer id);

    @Procedure(name = "deleteMessage", procedureName = "deleteMessage")
    String deleteMessage(@Param("idIN") Integer id);
}
