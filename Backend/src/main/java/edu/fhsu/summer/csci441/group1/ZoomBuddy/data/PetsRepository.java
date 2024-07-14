package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetsRepository extends CrudRepository<Pet, Integer> {

    @Query("SELECT p FROM Pet p WHERE p.owner.uid = :uid")
    // @Query(value = "SELECT p.*, u.* FROM Pet p, User u WHERE p.uid = :uid and
    // p.uid = u.uid", nativeQuery = true)
    Iterable<Pet> findAllPetsByUser(@Param("uid") String uid);

}
