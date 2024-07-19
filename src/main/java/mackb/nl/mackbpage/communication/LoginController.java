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
        System.out.println("loginForm - GETmapping");

        return "login";                         // Goes to login page.
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {

        System.out.println("Login attempt: " + username);//TEST
        User user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            if (User.checkPassword(password, user.getPassword())) {
                System.out.println("Password match successful");
                session.setAttribute("user", user);
                model.addAttribute("user", user);
                return "redirect:/tools";
            } else {
                System.out.println("Password match failed");
            }
        } else {
            System.out.println("User not found");
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

//    @GetMapping("/success")                                             // @GetMapping to retrieve the success page (on successful login)
//    public String successPage(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");                // Retrieves the user object from the sesssion
//        if (user != null) {
//            model.addAttribute("user", user);                  // If user is not null go to tools page.
//            return "tools"; // Redirect to tools page
//        } else {
//            return "redirect:/login";                                      // If user is null then go back to login.
//        }
//    }
}