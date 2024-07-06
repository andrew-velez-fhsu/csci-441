package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotosRepository extends CrudRepository<Photo, Integer>{
    @Query("Select p From Photo p Where p.petId = :petId")
    Iterable<Photo> findAllPhotosByPet(@Param("petId") int petId);
}
