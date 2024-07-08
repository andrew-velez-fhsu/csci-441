package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {
    // Scheme defined at
    // https://learn.microsoft.com/en-us/rest/api/maps/search/get-geocoding?view=rest-maps-2024-04-01&tabs=HTTP#geocodingresponse

    // properties
    /**
     * Must be one of the nine valid GeoJSON object types - Point, MultiPoint,
     * LineString, MultiLineString, Polygon, MultiPolygon, GeometryCollection,
     * Feature and FeatureCollection
     */
    @JsonProperty("type")
    private String type;

    @JsonProperty("features")
    private List<FeaturesItem> features;

    @JsonProperty("nextLink")
    private String nextLink;

    // getters & setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public List<FeaturesItem> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesItem> features) {
        this.features = features;
    }
}
