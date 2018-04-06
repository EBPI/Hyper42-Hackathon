package nl.ebpi.hyperpoort.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;
import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hyperpoort")
public class BackendController {

	@PostMapping("/aanlever")
	public String aanleveren(@RequestParam Aanlevering aanlevering) throws URISyntaxException, IOException {
		String kenmerk = UUID.randomUUID().toString();
		// Start Thread die polt met kenmerk

		return kenmerk;
	}

}
