package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;

@RestController
@CrossOrigin
public class SearchController {
    private final PetsRepository petsRepository;

    public SearchController(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @GetMapping("/search")
    public Iterable<Pet> findPets() {
        // stub, need a real search
        var foundPets = this.petsRepository.findAll();

        // TODO: redact all information except name, breed, first photo, and the first
        // 200 characters of the description
        /*
         * for (Pet pet : foundPets) {
         * if (pet == null) continue;
         * Class<?> clazz = pet.getClass();
         * Field[] fields = clazz.getDeclaredFields();
         * 
         * for (Field field : fields) {
         * field.setAccessible(true);
         * try {
         * if (!field.getName().equals("name") && !field.getName().equals("breed") &&
         * !field.getName().equals("description")) {
         * field.set(obj, null);
         * } else if (field.getName().equals("description")) {
         * String description = (String) field.get(obj);
         * if (description != null && description.length() > 200) {
         * field.set(obj, description.substring(0, 200));
         * }
         * }
         * } catch (IllegalAccessException e) {
         * e.printStackTrace();
         * }
         * }
         * }
         * 
         */

        // return found Pets
        return foundPets;
    }

}
