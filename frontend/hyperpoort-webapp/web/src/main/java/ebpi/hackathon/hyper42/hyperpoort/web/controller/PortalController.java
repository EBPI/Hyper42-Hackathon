package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import ebpi.hackathon.hyper42.hyperpoort.web.backend.AanleveringSubmitter;
import ebpi.hackathon.hyper42.hyperpoort.web.util.Hasher;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PortalController {

	@Autowired
	private AanleveringSubmitter aanleveringSubmitter;

	/**
	 * Homepage.
	 * 
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
	 * 
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @return Thymeleaf dynamic injected page for first register page (authentication)
	 */
	@RequestMapping("/register")
	public String register(Map<String, Object> model) {
		String message = "Todo: verbind met kvk app, maak en installeer businesscard op blockchain, geef businesscard terug";
		model.put("registerMessage", message);
		return "hyperpoort_webapp/register";
	}

	/**
	 * Submitting messages page.
	 * 
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @return Thymeleaf dynamic injected page for submitting messages
	 */
	@RequestMapping("/submit")
	public String postMessage(Map<String, Object> model) {
		String message = "Todo: maak formulier voor aanleveren (gebruik business card identiteit)";
		model.put("submitMessage", message);
		return "hyperpoort_webapp/submit";
	}

	@PostMapping("/submitmessage")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String ontvanger, RedirectAttributes redirectAttributes) {

		try {
			byte[] hash = Hasher.giveHash(file.getBytes());
			aanleveringSubmitter.submit(hash, ontvanger);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/";
	}

	/**
	 * View status history page.
	 * 
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @return Thymeleaf dynamic injected page for viewing status history
	 */
	@RequestMapping("/statusHistory")
	public String viewStatusHistory(Map<String, Object> model) {
		String message = "Todo: haal statussen op (gebruik business card identiteit)";
		model.put("statusMessage", message);
		return "hyperpoort_webapp/status";
	}
}
