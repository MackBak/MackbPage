package mackb.nl.mackbpage.communication;

import mackb.nl.mackbpage.business.model.Pilot;
import org.springframework.stereotype.Controller;
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
        return new Pilot(values[0], Integer.parseInt(values[1].trim()), Integer.parseInt(values[14].trim()));
    }
}
