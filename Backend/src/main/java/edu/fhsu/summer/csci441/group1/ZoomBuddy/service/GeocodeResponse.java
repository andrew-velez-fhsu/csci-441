package edu.fhsu.summer.csci441.group1.ZoomBuddy.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResponse {

    @JsonProperty("type")
    private String type;

    @JsonProperty("features")

    @JsonProperty("nextLink")
    private String nextLink;

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<FeaturesItem> features;

    public List<FeaturesItem> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeaturesItem> features) {
        this.features = features;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class FeaturesItem {

        @JsonProperty("bbox")
        private double boundingBox;

        @JsonProperty("queryTime")
        private int queryTime;

        @JsonProperty("numResults")
        private int numResults;

        // Getters and Setters
        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public int getQueryTime() {
            return queryTime;
        }

        public void setQueryTime(int queryTime) {
            this.queryTime = queryTime;
        }

        public int getNumResults() {
            return numResults;
        }

        public void setNumResults(int numResults) {
            this.numResults = numResults;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Result {

        @JsonProperty("type")
        private String type;

        @JsonProperty("position")
        private Position position;

        @JsonProperty("address")
        private Address address;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Position getPosition() {
            return position;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Position {

        @JsonProperty("lat")
        private double latitude;

        @JsonProperty("lon")
        private double longitude;

        // Getters and Setters
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Address {

        @JsonProperty("freeformAddress")
        private String freeformAddress;

        @JsonProperty("localName")
        private String localName;

        // Getters and Setters
        public String getFreeformAddress() {
            return freeformAddress;
        }

        public void setFreeformAddress(String freeformAddress) {
            this.freeformAddress = freeformAddress;
        }

        public String getLocalName() {
            return localName;
        }

        public void setLocalName(String localName) {
            this.localName = localName;
        }
    }

}
