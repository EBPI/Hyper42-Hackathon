package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import ebpi.hackathon.hyper42.hyperpoort.web.backend.AanleveringRegistrar;
import ebpi.hackathon.hyper42.hyperpoort.web.backend.AanleveringStarter;
import ebpi.hackathon.hyper42.hyperpoort.web.util.Hasher;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PortalController {

	@Autowired
	private AanleveringStarter aanleveringStarter;
	@Autowired
	private AanleveringRegistrar aanleveringRegistrar;

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
		String message = "Send your message";
		model.put("submitMessage", message);
		return "hyperpoort_webapp/submit";
	}

	/**
	 * Handle the file upload for aanleveren.
	 *
	 * @param file the file
	 * @param ontvanger the ontvanger
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 * @throws IOException
	 */
	@PostMapping("/submitmessage")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String receiver, Map<String, Object> model) throws IOException {

		byte[] hash = Hasher.giveHash(file.getBytes());
		String kenmerk = aanleveringStarter.submit(hash, receiver);
		aanleveringRegistrar.submit(hash, receiver, kenmerk);
		model.put("kenmerk", kenmerk);
		return "hyperpoort_webapp/submitted";
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
