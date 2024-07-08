package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intersection {

    @JsonProperty("baseStreet")
    private String baseStreet;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("intersectionType")
    private String intersectionType;
    @JsonProperty("secondaryStreet1")
    private String secondaryStreet1;
    @JsonProperty("secondaryStreet2")
    private String secondaryStreet2;

    public String getBaseStreet() {
        return baseStreet;
    }

    public void setBaseStreet(String baseStreet) {
        this.baseStreet = baseStreet;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIntersectionType() {
        return intersectionType;
    }

    public void setIntersectionType(String intersectionType) {
        this.intersectionType = intersectionType;
    }

    public String getSecondaryStreet1() {
        return secondaryStreet1;
    }

    public void setSecondaryStreet1(String secondaryStreet1) {
        this.secondaryStreet1 = secondaryStreet1;
    }

    public String getSecondaryStreet2() {
        return secondaryStreet2;
    }

    public void setSecondaryStreet2(String secondaryStreet2) {
        this.secondaryStreet2 = secondaryStreet2;
    }

}