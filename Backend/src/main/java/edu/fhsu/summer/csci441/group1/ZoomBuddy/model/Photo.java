package edu.fhsu.summer.csci441.group1.ZoomBuddy.model;


import jakarta.persistence.*;

@Entity
@Table(name="photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private int petId;
    private String url;

    // constructor
    public Photo(){

    }

    // get photo url
    public String getUrl() {
        return url;
    }

    // set photo url
    public void setUrl(String url) {
        this.url = url;
    }

    // get photo id
    public int getId() {
        return id;
    }

    // set photo id
    public void setId(int id) {
        this.id = id;
    }

    // get pet id
    public int getPetId() {
        return petId;
    }

    //set pet id
    public void setPetId(int petId) {
        this.petId = petId;
    }
}
