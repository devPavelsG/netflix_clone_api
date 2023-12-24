package dev.pavelsgarklavs.netflix_clone.controllers;

import dev.pavelsgarklavs.netflix_clone.dtos.requests.ChangePasswordRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.UserResponse;
import dev.pavelsgarklavs.netflix_clone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<UserResponse> me(Principal connectedUser) {
        return ResponseEntity.ok(service.me(connectedUser));
    }
}
