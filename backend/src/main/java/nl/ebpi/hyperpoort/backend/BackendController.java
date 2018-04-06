package nl.ebpi.hyperpoort.backend;

import nl.ebpi.hyperpoort.backend.model.Aanlevering;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@Controller
public class BackendController {

    @PostMapping("/aanlever")
    public String aanleveren(@RequestParam Aanlevering aanlevering)
            throws URISyntaxException, IOException {
        String kenmerk = UUID.randomUUID().toString();
        //Start Thread die polt met kenmerk

        return kenmerk;
    }

}
