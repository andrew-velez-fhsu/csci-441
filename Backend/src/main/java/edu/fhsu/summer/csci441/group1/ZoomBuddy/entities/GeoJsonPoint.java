package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoJsonPoint {
    // Derived from
    // https://learn.microsoft.com/en-us/rest/api/maps/search/get-geocoding?view=rest-maps-2024-04-01&tabs=HTTP#geojsonpoint
    @JsonProperty("bbox")
    private List<Double> boundingBox;

    /**
     * Coordinates specify the longitude, latitude and elevation (optional), in that
     * order
     */
    @JsonProperty("coordinates")
    private List<Double> coordinates;

    /**
     * Should be Point
     */
    @JsonProperty("type")
    private String type;

    // getters and setters
    public List<Double> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Double> boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}