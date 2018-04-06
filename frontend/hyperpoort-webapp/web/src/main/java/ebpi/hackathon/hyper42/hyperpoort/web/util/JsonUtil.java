package ebpi.hackathon.hyper42.hyperpoort.web.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public class JsonUtil {
	private JsonUtil() {
	};

	public static String findValue(String jsonString, String name) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode readTree = mapper.readTree(jsonString);
			JsonNode value = readTree.findValue("attest");
			System.out.println(value);
			Iterator<JsonNode> fields = value.elements();

			String text = StreamSupport.stream(Spliterators.spliteratorUnknownSize(fields, Spliterator.ORDERED), false)
					.filter(jn -> jn.findValue("property").asText().equals(name))
					.findFirst().get().findValue("value").asText();
			return text;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
