package mackb.nl.mackbpage.communication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String showHomePage() {
        return "index"; // This corresponds to the index.html template
    }

    @GetMapping("/tools")
    public String showToolsPage() {
        return "tools"; // This corresponds to the tools.html template
    }
}