package mackb.nl.mackbpage.communication;

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
        return "register"; // Goes to register page
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String username, @RequestParam String fullname, @RequestParam String password, Model model) {
        // Checks if user already exists
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists. Choose a different username.");
            return "register"; // Stays on the registration page
        }

        // Creates new user with hashed password and saves to repository
        User newUser = new User(username, fullname, password);
        userRepository.save(newUser);

        // Redirects to the login page with a success message
        model.addAttribute("message", "Registration successful. Please log in.");
        return "redirect:/login";
    }
}
