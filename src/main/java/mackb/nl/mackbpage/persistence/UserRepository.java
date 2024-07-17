package mackb.nl.mackbpage.persistence;

import mackb.nl.mackbpage.business.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
