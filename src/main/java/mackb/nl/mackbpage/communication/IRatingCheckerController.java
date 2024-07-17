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
        return "iRatingChecker";                    // Shows the iRatingChecker page.
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("csvFile") MultipartFile csvFile, @RequestParam("txtFile") MultipartFile txtFile) throws Exception { // With @RequestParam I allow the method to accept a  csv file (and txt file later)
        List<String> targetCustomerIds = new BufferedReader(new InputStreamReader(txtFile.getInputStream(), StandardCharsets.UTF_8)).lines().toList();      // Using a BR to read the lines in the txt file and adds this to a list.

        List<Pilot> pilots = new BufferedReader(new InputStreamReader(csvFile.getInputStream(), StandardCharsets.UTF_8))    // Using a BR to read the CSV file.
                .lines().skip(1)                                                                                         // Skips CSV header
                .map(this::mapToPilot)                                                                                      // Maps each line to a Pilot object
                .filter(pilot -> targetCustomerIds.contains(String.valueOf(pilot.getCustomerId())))                         // Running a filter that filters to only include the matching customerIDs
                .collect(Collectors.toList());                                                                              // Then collects this and adds it to the list.

        ModelAndView modelAndView = new ModelAndView("results");                    // Creating a ModelAndView Object
        modelAndView.addObject("pilots", pilots);                                // The filtered pilots now are added to the ModelAndView object with the pilots key.
        return modelAndView;                                                                 // Returns the ModelAndView so the results can be accessed as a list.
    }

    private Pilot mapToPilot(String line) {
        String[] values = line.split(",");          // Splits the lines when the , is found
        String fullName = values[0];                      // fullName is the first value found in the CSV.
        int customerId;                                   // Declaring the customerId & iRating
        int iRating;

        // CustomerID & iRating are the second and fifteenth column. Using a try/catch block since some users have false data that causes errors.
        try {
            customerId = Integer.parseInt(values[1].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing customerId: " + values[1]);
            customerId = -1;
        }

        try {
            iRating = Integer.parseInt(values[14].trim());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing iRating: " + values[14]);
            iRating = -1;
        }

        return new Pilot(fullName, customerId, iRating);
    }
}
