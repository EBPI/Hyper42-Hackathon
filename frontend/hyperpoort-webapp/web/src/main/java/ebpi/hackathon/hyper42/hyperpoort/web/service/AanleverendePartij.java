package ebpi.hackathon.hyper42.hyperpoort.web.service;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "kvknummer",
        "Adres"})
public class AanleverendePartij {

    @JsonProperty("kvknummer")
    private String kvknummer;
    @JsonProperty("Adres")
    private String adres;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("kvknummer")
    public String getKvknummer() {
        return kvknummer;
    }

    @JsonProperty("kvknummer")
    public void setKvknummer(String kvknummer) {
        this.kvknummer = kvknummer;
    }

    @JsonProperty("Adres")
    public String getAdres() {
        return adres;
    }

    @JsonProperty("Adres")
    public void setAdres(String adres) {
        this.adres = adres;
    }


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("kvknummer", kvknummer).append("adres", adres).append("additionalProperties", additionalProperties).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(adres).append(additionalProperties).append(kvknummer).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof AanleverendePartij) == false) {
            return false;
        }
        AanleverendePartij rhs = ((AanleverendePartij) other);
        return new EqualsBuilder().append(adres, rhs.adres).append(additionalProperties, rhs.additionalProperties).append(kvknummer, rhs.kvknummer).isEquals();
    }
}
