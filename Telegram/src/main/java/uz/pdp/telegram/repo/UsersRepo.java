package uz.pdp.telegram.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.telegram.entity.Users;

import java.util.List;
import java.util.Optional;

public interface UsersRepo extends JpaRepository<Users, Integer> {

    Users findByUsernameAndPassword(String username, String password);
    Optional<Users> findByUsername(String username);

    Optional<Users> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM Users u WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :input, '%')) OR " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :input, '%'))")
    List<Users> findByUsernameOrFirstname(@Param("input") String input);
}
