package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class PortalController {

    /**
     * Homepage.
     * @param model Model for dynamic form injection (Thymeleaf)
     * @return Thymeleaf dynamic injected page for homepage
     */
    @RequestMapping("/")
    public String home(Map<String, Object> model) {
        String message = "This text is inserted from within the code with Thymeleaf!";
        model.put("homeMessage", message);
        return "/hyperpoort_webapp/home";
    }

    /**
     * Registration page (authentication).
     * @param model Model for dynamic form injection (Thymeleaf)
     * @return Thymeleaf dynamic injected page for first register page (authentication)
     */
    @RequestMapping("/register")
    public String register(Map<String, Object> model) {
        String message = "Todo: verbind met kvk app, maak en installeer businesscard op blockchain, geef businesscard terug";
        model.put("registerMessage", message);
        return "/hyperpoort_webapp/register";
    }

    /**
     * Submitting messages page.
     * @param model Model for dynamic form injection (Thymeleaf)
     * @return Thymeleaf dynamic injected page for submitting messages
     */
    @RequestMapping("/submit")
    public String postMessage(Map<String, Object> model) {
        String message = "Todo: maak formulier voor aanleveren (gebruik business card identiteit)";
        model.put("submitMessage", message);
        return "/hyperpoort_webapp/submit";
    }

    /**
     * View status history page.
     * @param model Model for dynamic form injection (Thymeleaf)
     * @return Thymeleaf dynamic injected page for viewing status history
     */
    @RequestMapping("/statusHistory")
    public String viewStatusHistory(Map<String, Object> model) {
        String message = "Todo: haal statussen op (gebruik business card identiteit)";
        model.put("statusMessage", message);
        return "/hyperpoort_webapp/status";
    }

    /**
     * Download card.
     * @param id id for card
     * @return Thymeleaf dynamic injected page for viewing status history
     *
     * todo: Dit is uiteraard geen nette oplossing. Dit moet secuurder, geen pathvariable e.d.
     */
    @ResponseBody
    @RequestMapping("/downloadCard/{id}")
    public String downloadCard(@PathVariable String id) {
        System.out.println("ID = " + id);
        return "todo";
    }
}
