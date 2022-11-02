package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Class RestController represent rest api which allows to perform operations on users.
 */

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Method for read all users
     * @param page
     * @param size
     * @return status body
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> readAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                 @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        List<UserDto> users = userService.readAll(page, size);
        for (UserDto user : users) {
            createLinks(user);
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Method for read all user by id
     * @param id
     * @return status body
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> readById(@PathVariable Long id) {
        UserDto user = userService.readById(id);
        createLinks(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Method for create user
     * @param newUser
     * @return status body
     */
    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(newUser));
    }

    /**
     * Method for create links
     * @param user
     */
    private void createLinks(UserDto user) {
        user.add(linkTo(methodOn(UserController.class).readById(user.getId())).withSelfRel());
    }
}