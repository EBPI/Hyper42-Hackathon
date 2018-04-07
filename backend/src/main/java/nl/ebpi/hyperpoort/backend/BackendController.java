package nl.ebpi.hyperpoort.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hyperpoort")
public class BackendController {

	@PostMapping(value = "/aanleveren", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String aanleveren(@RequestBody Aanlevering aanlevering) throws URISyntaxException, IOException {
		String kenmerk = UUID.randomUUID().toString();

		return kenmerk;
	}

}
