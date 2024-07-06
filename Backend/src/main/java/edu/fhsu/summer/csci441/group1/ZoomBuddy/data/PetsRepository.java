package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetsRepository extends CrudRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.uid = :uid")
    Iterable<Pet> findAllPetsByUser(@Param("uid") String uid);

}
