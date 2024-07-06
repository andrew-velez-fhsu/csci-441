package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String breed;
    private String description;
    private String uid;
    private Date birthday;
    private boolean isGoodWithChildren;
    private boolean isResourceProtective;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
}
