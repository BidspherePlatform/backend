package com.bidsphere.bidsphere.controllers;

import com.bidsphere.bidsphere.dtos.UserDTO;
import com.bidsphere.bidsphere.entities.Users;
import com.bidsphere.bidsphere.repositories.UsersRepository;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class User extends SessionizedController {

    private final UsersRepository usersRepository;

    public User(
            UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProfile() {
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        Optional<Users> userQuery = this.usersRepository.findById(userId);

        if (userQuery.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new UserDTO(userQuery.get()));
    }
}
