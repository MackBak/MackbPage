package mackb.nl.mackbpage.business.model;

import jakarta.persistence.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

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

    @Column(nullable = false, length = 255)                                             // Set length to 255 because of SHA-256 hashing
    private String password;

    public User(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.password = hashPassword(password);                                         // Calling method hashPassword to the password is hashed.
    }

    // Method to created a hashed password which can be sent to the MySQL server.
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");        // Creates an instance of a new MessageDigest (messageDigest = default class in Java security for hasing in SHA256).
            byte[] encodedhash = digest.digest(password.getBytes());                    // Converts the (hashed) password to a byte array. With the digest method I process the byte array to produce the hash.
            return HexFormat.of().formatHex(encodedhash);                               // Converts the byte array into a hexadecimal string.
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    // Method to check if entered password (when hashed) matches the stored hashed password.
    public static boolean checkPassword(String enteredPassword, String storedPassword) {
        return hashPassword(enteredPassword).equals(storedPassword);
    }


    // Getters & Setters

    public User() {
        this("Default", "Default", "Default");
    }

    public String getPassword() {
        return password;
    }

}
