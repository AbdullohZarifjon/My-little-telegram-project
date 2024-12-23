package uz.pdp.telegram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.telegram.entity.Messages;
import uz.pdp.telegram.entity.Users;

import java.util.List;

public interface MessageRepo extends JpaRepository<Messages, Long> {
    List<Messages> findMessagesByFromUser_Id(int fromUserId);

    List<Messages> findMessagesByFromUser_IdAndToUser_Id(int fromUserId, int toUserId);

    List<Messages> findMessagesByFromUser_IdAndToUser_IdOrFromUser_IdAndToUser_Id(int fromUserId, int toUserId, int fromUserId1, int toUserId1);

    @Query("SELECT DISTINCT m.toUser FROM Messages m WHERE m.fromUser.id = :fromUserId")
    List<Users> findUniqueToUsersByFromUserId(@Param("fromUserId") int fromUserId);
}
