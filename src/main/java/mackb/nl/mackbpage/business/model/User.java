package mackb.nl.mackbpage.business.model;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, length = 45)
    private String fullname;

    @Column(nullable = false, length = 256)  // BCrypt hashed password length
    private String password;

    public User(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = hashPassword(password);
    }

    // Method to create a hashed password using BCrypt
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Method to check if entered password (when hashed) matches the stored hashed password
    public static boolean checkPassword(String enteredPassword, String storedPassword) {
        return BCrypt.checkpw(enteredPassword, storedPassword);
    }

    // Getters & Setters
    public User() {
        this("Default", "Default", "Default");
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    // More getters and setters as needed
}
