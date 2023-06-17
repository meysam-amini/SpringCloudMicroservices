package com.meysam.users.webapi.controller;

import com.meysam.common.model.entity.User;
import com.meysam.users.service.api.UserService;
import jakarta.validation.Valid;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping(value = "wallets/{userId}", produces =
            {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
   // @PreAuthorize("principal == #userId")
//    @PostAuthorize("principal == returnObject.getBody().getUserId()")
    public ResponseEntity<User> getUserWallets(@PathVariable("userId") String userId) {
        // TODO: 18.06.23 change jwt converter to add username to principal: 
        User returnValue = userService.getUserWallets(userId);

        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
