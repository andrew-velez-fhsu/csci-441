package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;

import jakarta.persistence.*;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Entity
@Table(name = "pets")
@Data
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String breed;
    private String description;
    private Date birthday;
    @JsonProperty("isGoodWithChildren")
    private boolean isGoodWithChildren;
    @JsonProperty("isResourceProtective")
    private boolean isResourceProtective;
    private String profileUrl;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User owner;

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // constructor
    public Pet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getbirthday() {
        return birthday;
    }

    public void setbirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGoodWithChildren() {
        return isGoodWithChildren;
    }

    public void setGoodWithChildren(boolean goodWithChildren) {
        isGoodWithChildren = goodWithChildren;
    }

    public boolean isResourceProtective() {
        return isResourceProtective;
    }

    public void setResourceProtective(boolean resourceProtective) {
        isResourceProtective = resourceProtective;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
