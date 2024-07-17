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

@Controller                     // Handles web requests, so @Controller
public class LoginController {

    @Autowired                                  // @Autowired so Spring automatically injects UserRepository in the controller.
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";                         // Goes to login page.
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) { // This captures the username & password submitted in the login form. Model is used to add attributes, HttpSession used to manage the user session.
        User user = userRepository.findByUsername(username);                                                                    // Gets the user from the database by the passed username.

        if (user != null && User.checkPassword(password, user.getPassword())) {                     // Checks if password is correct and user is not empty.
            session.setAttribute("user", user);                                                  // If login is succesfull, user is added to the model and stored in the session.
            model.addAttribute("user", user);
            return "redirect:/tools";                                                               // Redirect to tools page on successful login
        } else {
            model.addAttribute("error", "Invalid username or password");    // Displays error if login is not successful.
            return "login";
        }
    }

    @GetMapping("/success")                                             // @GetMapping to retrieve the success page (on successful login)
    public String successPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");                // Retrieves the user object from the sesssion
        if (user != null) {
            model.addAttribute("user", user);                  // If user is not null go to tools page.
            return "tools"; // Redirect to tools page
        } else {
            return "redirect:/login";                                      // If user is null then go back to login.
        }
    }
}
