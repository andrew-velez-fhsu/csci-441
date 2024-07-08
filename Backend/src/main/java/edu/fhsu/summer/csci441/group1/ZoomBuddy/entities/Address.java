package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty("addressLine")
    private String addressLine;

    @JsonProperty("adminDistricts")
    private List<AdminDistricts> adminDistricts;

    @JsonProperty("countryRegion")
    private CountryRegion countryRegion;

    @JsonProperty("formmattedAddress")
    private String formattedAddress;

    @JsonProperty("intersection")
    private Intersection intersection;

    @JsonProperty("locality")
    private String locality;

    @JsonProperty("neighborhood")
    private String neighborhood;

    @JsonProperty("postalCode")
    private String postalCode;

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public List<AdminDistricts> getAdminDistricts() {
        return adminDistricts;
    }

    public void setAdminDistricts(List<AdminDistricts> adminDistricts) {
        this.adminDistricts = adminDistricts;
    }

    public CountryRegion getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(CountryRegion countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

}