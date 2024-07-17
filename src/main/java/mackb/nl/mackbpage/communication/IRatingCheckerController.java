package mackb.nl.mackbpage.communication;

import mackb.nl.mackbpage.business.model.Pilot;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/irating")
public class IRatingCheckerController {

    @GetMapping("/iRatingChecker")
    public String showIRatingCheckerPage() {
        return "iRatingChecker"; // This corresponds to the iRatingChecker.html template
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("csvFile") MultipartFile csvFile,
                                         @RequestParam("txtFile") MultipartFile txtFile) throws Exception {
        List<String> targetCustomerIds = new BufferedReader(new InputStreamReader(txtFile.getInputStream(), StandardCharsets.UTF_8))
                .lines().collect(Collectors.toList());

        List<Pilot> pilots = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))
                .lines().skip(1) // Skip CSV header
                .map(this::mapToPilot)
                .filter(pilot -> targetCustomerIds.contains(String.valueOf(pilot.getCustomerId())))
                .collect(Collectors.toList());

        ModelAndView modelAndView = new ModelAndView("results");
        modelAndView.addObject("pilots", pilots);
        return modelAndView;
    }

    private Pilot mapToPilot(String line) {
        String[] values = line.split(",");
        String fullName = values[0];
        int customerId;
        int iRating;

        try {
            customerId = Integer.parseInt(values[1].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing customerId: " + values[1]);
            customerId = -1; // or handle as needed
        }

        try {
            iRating = Integer.parseInt(values[14].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing iRating: " + values[14]);
            iRating = -1; // or handle as needed
        }

        return new Pilot(fullName, customerId, iRating);
    }
}
