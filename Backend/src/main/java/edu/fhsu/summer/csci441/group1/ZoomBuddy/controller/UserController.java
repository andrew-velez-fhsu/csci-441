package edu.fhsu.summer.csci441.group1.ZoomBuddy.controller;

import edu.fhsu.summer.csci441.group1.ZoomBuddy.data.UsersRepository;
import edu.fhsu.summer.csci441.group1.ZoomBuddy.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UsersRepository usersRepository;


    public UserController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/users")
    public Iterable<User> findAllUsers(){
        return this.usersRepository.findAll();
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user){
        return this.usersRepository.save(user);
    }
}
