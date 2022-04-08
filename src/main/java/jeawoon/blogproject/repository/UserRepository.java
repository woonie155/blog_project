package jeawoon.blogproject.repository;

import jeawoon.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
//    User findByUsernameAndPassword(String username, String password);
