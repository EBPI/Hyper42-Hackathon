package ebpi.hackathon.hyper42.hyperpoort.web.service;

public class AanleverendePartij {
    private String kvkNumber;
    private String adres;

    public AanleverendePartij(String kvkNumber, String adres) {
        this.adres = adres;
        this.kvkNumber = kvkNumber;
    }
}
