package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class PortalController {

    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        String message = "This text is inserted from within the code with Thymeleaf!";
        model.put("homeMessage", message);
        return "/hyperpoort_webapp/home";
    }

    @RequestMapping("/register")
    public String register(Map<String, Object> model) {
        String message = "Todo: verbind met kvk app, maak en installeer businesscard op blockchain, geef businesscard terug";
        model.put("registerMessage", message);
        return "hyperpoort_webapp/register";
    }

    @RequestMapping("/submit")
    public String postMessage(Map<String, Object> model) {
        String message = "Todo: maak formulier voor aanleveren (gebruik business card identiteit)";
        model.put("submitMessage", message);
        return "hyperpoort_webapp/submit";
    }

    @RequestMapping("/statusHistory")
    public String viewStatusHistory(Map<String, Object> model) {
        String message = "Todo: haal statussen op (gebruik business card identiteit)";
        model.put("statusMessage", message);
        return "hyperpoort_webapp/status";
    }
}
