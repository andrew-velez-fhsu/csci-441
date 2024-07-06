package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class PetController {
    private final PetsRepository petsRepository;

    // constructor here
    public PetController(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    // find all pets ===============================================
    @GetMapping("/pets")
    public Iterable<Pet> findAllPetsByUser(Authentication auth) {
        return this.petsRepository.findAll();
    }

    // get pet by id ========================================================
    @GetMapping("/pets/{id}")
    public Pet getPetByid(@PathVariable("id") int id, Authentication auth) {
        var pet = this.petsRepository.findById(id).orElse(null);
        if (pet != null) {
            if (!pet.getUid().equals(auth.getName()))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pet does not belong to current user");
            else
                return pet;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");
    }

    // update pets
    // =====================================================================
    @PutMapping("/pets/{id}")
    public Pet updatedPet(@PathVariable("id") int id, Authentication auth, @RequestBody Pet pet) {
        if (!pet.getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Pet does not belong to current user");

        if (pet != null && id == pet.getId())
            return this.petsRepository.save(pet);
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");
    }

    // add pet
    // ==========================================================================
    @PostMapping("/pets")
    public Pet addPet(@RequestBody Pet pet) {
        return this.petsRepository.save(pet);
    }

    // Delete
    @DeleteMapping("/pets/{id}")
    @ResponseBody
    public void deletePet(@PathVariable("id") int id, Authentication auth) {
        var pet = this.petsRepository.findById(id).orElse(null);

        if (pet != null) {
            if (!pet.getUid().equals(auth.getName()))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pet does not belong to current user");
            else
                petsRepository.deleteById(id);
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");
    }

}
