package nl.ebpi.hyperpoort.backend.backend;

import java.io.IOException;
import java.net.URISyntaxException;
import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BackendController {

	@PostMapping("/aanlever")
	public String aanleveren(@RequestParam Aanlevering aanlevering)
			throws URISyntaxException, IOException {
		return null;
	}

}
