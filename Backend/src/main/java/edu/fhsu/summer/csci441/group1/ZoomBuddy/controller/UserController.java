package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
public class UserController {

    private final UsersRepository usersRepository;

    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers() {
        return this.usersRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserByUid(@PathVariable("id") String uid, Authentication auth) {
        var user = this.usersRepository.findByFirebaseUid(uid);
        if (user != null)
            return user;
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @PutMapping("/users/{uid}")
    public User updateUser(@PathVariable("uid") String uid, Authentication auth, @RequestBody User user) {
        if (user != null && uid.equals(user.getUid()))
            return this.usersRepository.save(user);
        else if (user != null && !uid.equals(user.getUid()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "User Id does not match user");
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found");
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        return this.usersRepository.save(user);
    }

    @DeleteMapping("/users/{uid}")
    public void deleteUser(@PathVariable("uid") String uid, Authentication auth) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Not implemented");
    }
}
