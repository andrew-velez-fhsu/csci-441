package edu.fhsu.summer.csci441.group1.ZoomBuddy.data;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;

public interface PetsRepository extends CrudRepository<Pet, Integer>{
    Iterable<Pet> findAllPetsByUser(String uid);
}
