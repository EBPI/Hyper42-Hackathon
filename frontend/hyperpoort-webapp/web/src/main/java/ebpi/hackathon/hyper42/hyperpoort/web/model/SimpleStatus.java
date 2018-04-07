package ebpi.hackathon.hyper42.hyperpoort.web.model;

public class SimpleStatus {
	private final String kenmerk;
	private final Integer statusnr;

	public SimpleStatus(String kenmerk, Integer statusnr) {
		this.kenmerk = kenmerk;
		this.statusnr = statusnr;
	}

	public String getKenmerk() {
		return kenmerk;
	}

	public Integer getStatusnr() {
		return statusnr;
	}

	public String getString() {
		return kenmerk + " - " + statusnr;
	}
}
