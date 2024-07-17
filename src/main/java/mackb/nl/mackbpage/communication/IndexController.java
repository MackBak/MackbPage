package mackb.nl.mackbpage.communication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String showHomePage() {
        return "index";                             // Goes to the Index page.
    }

    @GetMapping("/tools")
    public String showToolsPage() {
        return "tools";                             // Goes to the tools page.
    }
}