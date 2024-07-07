package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.AzureMapsService;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class UserController {

    private final UsersRepository usersRepository;
    private final PetsRepository petsRepository;
    private final AzureMapsService azureMapsService;

    public UserController(UsersRepository usersRepository, PetsRepository petsRepository,
            AzureMapsService azureMapsService) {
        this.usersRepository = usersRepository;
        this.petsRepository = petsRepository;
        this.azureMapsService = azureMapsService;
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers() {
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserByUid(@PathVariable("id") String uid, Authentication auth) {
        var user = this.usersRepository.findByFirebaseUid(uid);
        if (user != null) {
            if (!user.getUid().equals(auth.getName()))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Pet does not belong to current user");
            return user;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @GetMapping("/users/{uid}/pets")
    public Iterable<Pet> getPetsByUser(@PathVariable("uid") String uid, Authentication auth) {
        var user = this.usersRepository.findByFirebaseUid(uid);
        if (user != null) {
            return this.petsRepository.findAllPetsByUser(uid);
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @PutMapping("/users/{uid}")
    public User updateUser(@PathVariable("uid") String uid, Authentication auth, @RequestBody User user) {
        // validate request
        if (!user.getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Current user does not match user body");

        if (user != null && uid.equals(user.getUid())) {
            // get the location information
            var userLocation = azureMapsService.getGeocoding(user);
            // update the user

            return this.usersRepository.save(user);
        } else if (user != null && !uid.equals(user.getUid()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User Id does not match user");
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user, Authentication auth) {
        if (!user.getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Current user does not match user body");
        return this.usersRepository.save(user);
    }

    @DeleteMapping("/users/{uid}")
    public void deleteUser(@PathVariable("uid") String uid, Authentication auth) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
    }
}
