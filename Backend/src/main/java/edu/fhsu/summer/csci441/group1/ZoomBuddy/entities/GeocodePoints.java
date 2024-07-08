package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodePoints {
    @JsonProperty("calculationMethod")
    private String calculationMethod;
    @JsonProperty("geometry")
    private GeoJsonPoint geometry;
    @JsonProperty("usageTypes")
    private List<String> usageTypes;

    public String getCalculationMethod() {
        return calculationMethod;
    }

    public void setCalculationMethod(String calculationMethod) {
        this.calculationMethod = calculationMethod;
    }

    public GeoJsonPoint getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonPoint geometry) {
        this.geometry = geometry;
    }

    public List<String> getUsageTypes() {
        return usageTypes;
    }

    public void setUsageTypes(List<String> usageTypes) {
        this.usageTypes = usageTypes;
    }
}