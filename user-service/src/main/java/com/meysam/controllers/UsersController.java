package com.meysam.controllers;

import com.meysam.model.entity.User;
import com.meysam.model.UserResponseModel;
import com.meysam.service.UserService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final Environment env;
    private final UserService userService;

    public UsersController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working On Port " + env.getProperty("local.server.port") + " token: " + env.getProperty("token.secret");
    }


    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity createUser(@Valid @RequestBody User model) {

        model = userService.createUser(model);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
//                ("User " + model.getFirstName() + " " + model.getLastName() + " created! \n"
//                        + "ID: " + model.getUserId());
    }


    @GetMapping(value = "/{userId}", produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
   // @PreAuthorize("principal == #userId")
    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
        UserResponseModel returnValue = userService.getUserByUserID(userId);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
