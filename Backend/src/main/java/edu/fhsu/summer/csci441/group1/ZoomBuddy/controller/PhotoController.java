package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import com.google.api.Authentication;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PhotosRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Photo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class PhotoController {
    private final PhotosRepository photoRepository;

    // constructor
    public PhotoController(PhotosRepository photoRepository){
        this.photoRepository = photoRepository;
    }

    //get pet by id ========================================================
    @GetMapping("/pets/{petId}/photos")
     public Iterable<Photo> getPhotosByPet(@PathVariable("petId") int petId, Authentication auth) {
        var photos = this.photoRepository.findAllPhotosByPet(petId);
        if (photos != null)
            return photos;
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Photo not found"
            );
    }

    // add pet photo ==========================================================================
    @PostMapping("/photos")
    public Photo addPetPhoto(@RequestBody Photo photo) {
        return this.photoRepository.save(photo);
    }

    // Delete pet photo =======================================
    @DeleteMapping("/photos/{id}")
    public void  deletePetPhoto(@PathVariable("id") int id){
        photoRepository.deleteById(id);
    }
}

