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
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // Thymeleaf template name
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        User user = userRepository.findByUsername(username);

        if (user != null && User.checkPassword(password, user.getPassword())) {
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/tools"; // Redirect to tools page on successful login
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/success")
    public String successPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "tools"; // Redirect to tools page
        } else {
            return "redirect:/login";
        }
    }
}
