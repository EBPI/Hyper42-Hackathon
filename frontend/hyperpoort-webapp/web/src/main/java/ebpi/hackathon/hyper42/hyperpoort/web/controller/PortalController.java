package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import ebpi.hackathon.hyper42.hyperpoort.web.backend.AanleveringRegistrar;
import ebpi.hackathon.hyper42.hyperpoort.web.backend.AanleveringStarter;
import ebpi.hackathon.hyper42.hyperpoort.web.backend.StatussenRetriever;
import ebpi.hackathon.hyper42.hyperpoort.web.model.SimpleStatus;
import ebpi.hackathon.hyper42.hyperpoort.web.model.Status;
import ebpi.hackathon.hyper42.hyperpoort.web.util.Hasher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Controller
public class PortalController {

	@Autowired
	private AanleveringRegistrar aanleveringRegistrar;
	@Autowired
	private AanleveringStarter aanleveringStarter;
	@Autowired
	private StatussenRetriever statussenRetriever;

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
		return "/hyperpoort_webapp/register";
	}

	/**
	 * Submitting messages page.
	 * 
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @return Thymeleaf dynamic injected page for submitting messages
	 */
	@RequestMapping("/submit")
	public String submitMessage(Map<String, Object> model) {
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
	 * Download cards.
	 * 
	 * @param id id for cards
	 * @return Thymeleaf dynamic injected page for viewing status history
	 *
	 * todo: Dit is uiteraard geen nette oplossing. Dit moet secuurder, geen pathvariable e.d.
	 */
	@ResponseBody
	@RequestMapping("/downloadCard/{id}")
	public ResponseEntity<byte[]> downloadCard(@PathVariable String id) {
		String fileName = id + ".card";
		System.out.println("Getting cards with filename = " + fileName);
		File file = new File(fileName);
		Path path = Paths.get(file.toURI());
		byte[] data;
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		final HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "attachment; filename=" + fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

		return new ResponseEntity<>(data, headers, HttpStatus.CREATED);
	}

	/**
	 * View status history page.
	 * 
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @return Thymeleaf dynamic injected page for viewing status history
	 */
	@RequestMapping("/statusHistory")
	public String viewStatusHistory(Map<String, Object> model) {
		List<Status> statussen = statussenRetriever.getStatussen();

		List<SimpleStatus> simpleStatussen = new ArrayList<>();
		for (Status status : statussen) {
			String kenmerk = status.getKenmerk();
			for (Integer statusnr : status.getStatussen()) {
				simpleStatussen.add(new SimpleStatus(kenmerk, statusnr));
			}
		}
		model.put("statussen", simpleStatussen);
		return "hyperpoort_webapp/status";
	}
}
