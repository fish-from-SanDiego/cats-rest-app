package org.FishFromSanDiego.cats.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.dto.UserView;
import org.FishFromSanDiego.cats.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
@ComponentScan({"org.FishFromSanDiego.cats.services", "org.FishFromSanDiego.cats.controllers"})
public class UsersController {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping(path = "/{id}")
    @ResponseBody
    public UserDto showUser(@PathVariable("id") long id) {
        return usersService.getUserById(id);
    }

    @GetMapping
    public List<UserDto> showAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping
    public UserDto registerNewUser(
            @JsonView(UserView.Request.class) @RequestBody @Valid UserDto user) {
        return usersService.registerNewUser(user);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> updateUser(
            @JsonView(UserView.Request.class) @RequestBody @Valid UserDto user, @PathVariable("id") long id) {
        user.setId(id);
        usersService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
        usersService.removeUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
