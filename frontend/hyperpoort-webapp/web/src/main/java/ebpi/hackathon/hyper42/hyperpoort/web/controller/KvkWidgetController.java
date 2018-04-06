package ebpi.hackathon.hyper42.hyperpoort.web.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import ebpi.hackathon.hyper42.hyperpoort.web.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Controller
public class KvkWidgetController {
	private static final URI KVK_URI = URI.create("https://identity.mayersoftwaredevelopment.nl/api/attest");
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/script")
	public String javascript() {
		return "kvk/js";
	}

	@GetMapping("/identify")
	public String kvkWidget() {
		return "kvk/kvkwidget";
	}

	@PostMapping("/getData")
	public String doeKvk(Map<String, Object> model, @RequestParam("sessionKey") String session) {
		KvKBody kvKBody = new KvKBody();
		kvKBody.sessionKey = session;

		RequestEntity<KvKBody> request = new RequestEntity<KvKBody>(kvKBody, HttpMethod.POST, KVK_URI);
		ResponseEntity<String> response = restTemplate.exchange(request, String.class);

		model.put("data", response.getBody());

		return "kvk/answer";
	}

	@PostMapping("/hello")
	public String hello(Map<String, Object> model, @RequestParam String data) {
		byte[] decode = Base64.getDecoder().decode(data);
		String value = JsonUtil.findValue(new String(decode), "businessName");
		model.put("name", value);
		return "kvk/hello";
	}

	private static class KvKBody {
		@Override
		//todo: geen sessionkey hardcoded!
		public String toString() {
			return "KvKBody [sessionKey=" + sessionKey + ", secret=" + secret + "]";
		}

		@JsonProperty
		private String sessionKey;
		@JsonProperty
		private String secret = "6f57be0d538757bb7a3343a9aa4e62023ec4aa86";
	}
}