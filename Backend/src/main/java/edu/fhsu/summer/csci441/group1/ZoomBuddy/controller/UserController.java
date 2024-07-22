package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.PetsRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.AzureMapsService;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.Pet;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.service.FirebaseUserService;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
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
    private final GeometryFactory geometryFactory;
    private final FirebaseUserService firebaseUserService;

    public UserController(UsersRepository usersRepository, PetsRepository petsRepository,
            AzureMapsService azureMapsService, FirebaseUserService firebaseUserService) {
        this.usersRepository = usersRepository;
        this.petsRepository = petsRepository;
        this.azureMapsService = azureMapsService;
        this.firebaseUserService = firebaseUserService;
        this.geometryFactory = new GeometryFactory();
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers() {
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserByUid(@PathVariable("id") String uid, Authentication auth) {
        var user = this.usersRepository.findById(uid).orElseThrow(); // type error (int & string conflict from
                                                                     // CrudRepository -> findById
        if (user != null) {
            if (!user.getUid().equals(auth.getName()))
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Current User does not match requested user");
            return user;
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @GetMapping("/users/{uid}/pets")
    public Iterable<Pet> getPetsByUser(@PathVariable("uid") String uid, Authentication auth) {
        var user = this.usersRepository.findById(uid).orElseThrow(); // type error (int & string conflict from
                                                                     // CrudRepository -> findById
        if (user != null) {
            return this.petsRepository.findAllPetsByUser(uid);
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @PutMapping("/users/{uid}")
    public User updateUser(@PathVariable("uid") String uid, Authentication auth, @RequestBody User user)
            throws Exception {
        // validate request
        if (!user.getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Current user does not match user body");

        if (user != null && uid.equals(user.getUid())) {
            // get the location information
            try {
                var userLocation = azureMapsService.getGeocoding(user);
                // update the user
                if (userLocation != null && userLocation.getFeatures().size() > 0) {
                    // use the first feature
                    var feature = userLocation.getFeatures().get(0);
                    var longitude = feature.getGeometry().getCoordinates().get(0).doubleValue();
                    var latitude = feature.getGeometry().getCoordinates().get(1).doubleValue();
                    var location = geometryFactory.createPoint(new Coordinate(longitude, latitude));

                    user.setLongitude(longitude);
                    user.setLatitude(latitude);
                    user.setLocation(location);
                }
            } catch (Exception ex) {
                throw new Exception("Unable to get user location from API Map service");
            }

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
        User user = usersRepository.findById(uid).orElseThrow(); // type error (int & string conflict from
                                                                 // CrudRepository -> findById
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
        }
        if (!user.getUid().equals(auth.getName()))
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Current user does not match user body");
        firebaseUserService.deleteUser(uid); // call firebase delete method
        usersRepository.deleteById(user.getUid());
    }
}
