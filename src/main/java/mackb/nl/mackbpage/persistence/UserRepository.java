package mackb.nl.mackbpage.persistence;

import mackb.nl.mackbpage.business.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { // Interface that extends the JpaRepo. This inherits a lot of CRUD functionality.
    User findByUsername(String username);                           // Default method from the Spring Data JPA.
}
