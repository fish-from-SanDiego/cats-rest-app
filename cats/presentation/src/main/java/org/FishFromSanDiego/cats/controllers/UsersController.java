package org.FishFromSanDiego.cats.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.FishFromSanDiego.cats.dto.ApplicationUserDetails;
import org.FishFromSanDiego.cats.dto.UserDto;
import org.FishFromSanDiego.cats.dto.UserUpdateDto;
import org.FishFromSanDiego.cats.dto.UserView;
import org.FishFromSanDiego.cats.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@ComponentScan({"org.FishFromSanDiego.cats.services", "org.FishFromSanDiego.cats.controllers", "org.FishFromSanDiego.cats.security"})
public class UsersController {
    private final UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(path = "/users/{id}")
    @ResponseBody
    @JsonView(UserView.ForAdmin.class)
    public UserDto showUserForAdmin(@PathVariable("id") long id) {
        return usersService.getUserById(id);
    }

    @GetMapping(path = "/user")
    @ResponseBody
    @JsonView(UserView.ForUser.class)
    public UserDto showUser(@AuthenticationPrincipal ApplicationUserDetails userDetails) {
        return usersService.getUserById(userDetails.getId());
    }

    @GetMapping("/users")
    @JsonView(UserView.ForAdmin.class)
    public List<UserDto> showAllUsers() {
        return usersService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> registerNewUserAsAdmin(@JsonView(UserView.Register.class) @RequestBody @Valid UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersService.registerNewUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping(path = "/users/{id}")
    public ResponseEntity<HttpStatus> updateUserAsAdmin(
            @RequestBody @Valid UserUpdateDto user, @PathVariable("id") long id) {
        var userDto = UserDto.builder()
                .id(id)
                .birthDate(user.getBirthDate())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .build();
        usersService.updateUser(userDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping(path = "users/{id}")
    public ResponseEntity<HttpStatus> deleteUserAsAdmin(@PathVariable("id") long id) {
        usersService.removeUserById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
