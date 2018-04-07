package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ebpi.hackathon.hyper42.hyperpoort.web.service.LedgerService;
import ebpi.hackathon.hyper42.hyperpoort.web.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Controller
public class KvkWidgetController {
	private static final URI KVK_URI = URI.create("https://identity.mayersoftwaredevelopment.nl/api/attest");
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private LedgerService ledgerService;

	/**
	 * Get javascript file that is needed for KvK ID app authentication.
	 * @return Script file in String
	 */
	@GetMapping("/script")
	public String javascript() {
		return "/kvk/js.js";
	}

	/**
	 * Get the data from the KvK ID app response.
	 * @param session Session from KvK ID app
	 * @return String body from responseEntity, this should be the organisation that is registered on the app
	 */
	@ResponseBody
	@PostMapping("/getData")
	public String doeKvk(@RequestParam("sessionKey") String session) {
		KvKBody kvKBody = new KvKBody();
		kvKBody.sessionKey = session;

		RequestEntity<KvKBody> request = new RequestEntity<>(kvKBody, HttpMethod.POST, KVK_URI);
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);

		return response.getBody();
	}

	/**
	 * When data is retrieved from the KvK app, construct a .card file from the ledger and display some organisation info to the user.
	 * @param model Model for dynamic form injection (Thymeleaf)
	 * @param data Base64 encoded
	 * @return Thymeleaf dynamic injected page for registration finalizing
	 */
	@PostMapping("/handleregistration")
	public String handleRegistration(Map<String, Object> model, @RequestParam String data) {
		byte[] decode = Base64.getDecoder().decode(data);
		String name = JsonUtil.findValue(new String(decode), "businessName");
		String kvkNumber = JsonUtil.findValue(new String(decode), "kvknummer");
        String straat = JsonUtil.findValue(new String(decode), "street");
        String huisnummer = JsonUtil.findValue(new String(decode), "houseNumber");
		String adres = straat + " " + huisnummer;

        byte[] businesscard = ledgerService.makeCard(kvkNumber, adres);
		model.put("name", name);
		return "/hyperpoort_webapp/registration";
	}

	/**
	 * KvK body for ID app authentication.
	 */
	private static class KvKBody {
		@Override
		public String toString() {
			return "KvKBody [sessionKey=" + sessionKey + ", secret=" + secret + "]";
		}

		@JsonProperty
		private String sessionKey;
		@JsonProperty
		private String secret = "e7eaf6cc30e75403c13d12bbd71f3c6ce2d4747c";
	}
}
