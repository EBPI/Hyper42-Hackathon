package nl.ebpi.hyperpoort.backend.hyperledger;

public class UrlConstants {

	private static final String PREFIX = "http://";
	private static final String IP_ADDRESS = "10.0.169.30";
	private static final String ADMIN_PORT = "3000";
	private static final String VERWERKER_PORT = "3002";
	private static final String ONTVANGER_PORT = "3003";
	private static final String SET_FINAL_STATUS_URL = PREFIX + IP_ADDRESS + ":" + ONTVANGER_PORT + "/api/SetFinalStatus";
	private static final String STATUS_UPDATE_URL = PREFIX + IP_ADDRESS + ":" + VERWERKER_PORT + "/api/StatusUpdate";
	private static final String AANLEVERINGEN_GETTER_URL = PREFIX + IP_ADDRESS + ":" + VERWERKER_PORT + "/api/queries/selectAanleveringenOpKenmerk";

	private UrlConstants() {
	}

	public static String getSetFinalStatusUrl() {
		return SET_FINAL_STATUS_URL;
	}

	public static String getStatusUpdateUrl() {
		return STATUS_UPDATE_URL;
	}

	public static String getAanleveringenGetterUrl() {
		return AANLEVERINGEN_GETTER_URL;
	}

}
