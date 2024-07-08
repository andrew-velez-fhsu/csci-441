package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Properties {
    @JsonProperty("address")
    private Address address;

    /**
     * One of high, medium, or low
     */
    @JsonProperty("confidence")
    private String confidence;

    @JsonProperty("geocodePoints")
    private List<GeocodePoints> geocodePoints;

    /**
     * One of Ambiguous, Good, or Uphierachy
     */
    @JsonProperty("matchCodes")
    private List<String> matchCodes;

    /**
     * One of:
     * Address
     * RoadBlock
     * RoadIntersection
     * Neighborhood
     * PopulatedPlace
     * Postcode1
     * AdminDivision1
     * AdminDivision2
     * CountryRegion
     */
    @JsonProperty("type")
    private String type;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public List<GeocodePoints> getGeocodePoints() {
        return geocodePoints;
    }

    public void setGeocodePoints(List<GeocodePoints> geocodePoints) {
        this.geocodePoints = geocodePoints;
    }

    public List<String> getMatchCodes() {
        return matchCodes;
    }

    public void setMatchCodes(List<String> matchCodes) {
        this.matchCodes = matchCodes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}