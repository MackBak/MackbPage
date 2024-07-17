package mackb.nl.mackbpage.communication;

import jakarta.servlet.http.HttpSession;
import mackb.nl.mackbpage.business.model.User;
import mackb.nl.mackbpage.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Return the Thymeleaf template for registration
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username, @RequestParam String fullname, @RequestParam String password, Model model) {
        // Check if user already exists
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists. Choose a different username.");
            return "register"; // Stay on the registration page
        }

        // Create new user with hashed password and save to repository
        User newUser = new User(username, fullname, password);
        userRepository.save(newUser);

        // Redirect to the login page with a success message
        model.addAttribute("message", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}
