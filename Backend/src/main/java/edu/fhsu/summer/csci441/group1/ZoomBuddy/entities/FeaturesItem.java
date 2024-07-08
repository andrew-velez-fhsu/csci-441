package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeaturesItem {
    // Derived from
    // https://learn.microsoft.com/en-us/rest/api/maps/search/get-geocoding?view=rest-maps-2024-04-01&tabs=HTTP#featuresitem

    @JsonProperty("bbox")
    private List<Double> boundingBox;

    @JsonProperty("geometry")
    private GeoJsonPoint geometry;

    @JsonProperty("id")
    private String id;

    @JsonProperty("properties")
    private Properties properties;

    @JsonProperty("type")
    private String type;

    // getters and setters
    public List<Double> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Double> boundingBox) {
        this.boundingBox = boundingBox;
    }

    public GeoJsonPoint getGeometry() {
        return geometry;
    }

    public void setGeometry(GeoJsonPoint geometry) {
        this.geometry = geometry;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}