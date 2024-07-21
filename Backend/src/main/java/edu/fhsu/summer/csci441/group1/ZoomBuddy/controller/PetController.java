package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class PetController {
    private final PetsRepository petsRepository;
    private final UsersRepository usersRepository;

    // constructor here
    public PetController(PetsRepository petsRepository, UsersRepository usersRepository) {
        this.petsRepository = petsRepository;
        this.usersRepository = usersRepository;
    }

    // get pet by id ========================================================
    @GetMapping("/pets/{id}")
    public Pet getPetByid(@PathVariable("id") int id, Authentication auth) {
        var pet = this.petsRepository.findById(id).orElse(null);
        if (pet != null) {
            return pet;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");
    }

    // update pets
    // =====================================================================
    @PutMapping("/pets/{id}")
    public Pet updatedPet(@PathVariable("id") int id, Authentication auth, @RequestBody Pet pet) {
        // get the existing pet object
        var existingPet = this.petsRepository.findById(id).orElse(null);

        if (existingPet == null || pet == null)
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");

        if (existingPet.getId() != pet.getId() || pet.getId() != id)
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Pet ID does not match updated pet");

        if (!existingPet.getOwner().getUid().equals(pet.getOwner().getUid()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Cannot change pet owner");

        if (!pet.getOwner().getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Pet does not belong to current user");

        // checks pass, save the pet
        return this.petsRepository.save(pet);

    }

    // add pet
    // ==========================================================================
    @PostMapping("/pets")
    public Pet addPet(@RequestBody Pet pet, Authentication auth) {
        var owner = usersRepository.findById(Integer.parseInt(auth.getName())).orElseThrow();
        pet.setOwner(owner);
        return this.petsRepository.save(pet);

    }

    // Delete
    @DeleteMapping("/pets/{id}")
    @ResponseBody
    public void deletePet(@PathVariable("id") int id, Authentication auth) {
        var pet = this.petsRepository.findById(id).orElse(null);

        if (pet != null) {
            if (!pet.getOwner().getUid().equals(auth.getName()))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pet does not belong to current user");
            else
                petsRepository.deleteById(id);
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Pet not found");
    }

}
