package dev.pavelsgarklavs.netflix_clone.controllers;

import dev.pavelsgarklavs.netflix_clone.dtos.requests.SubUserCreateRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.SubUserUpdateRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.SubUserGetAllResponse;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.SubUserResponse;
import dev.pavelsgarklavs.netflix_clone.services.SubUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sub-user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class SubUserController {
    private final SubUserService subUserService;

    @GetMapping("/get-all/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<SubUserGetAllResponse> getAll(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(subUserService.getAll(id));
    }
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<SubUserResponse> create(
            @RequestBody SubUserCreateRequest request
            ) {
        return ResponseEntity.ok(subUserService.create(request));
    }
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<SubUserResponse> update(
            @RequestBody SubUserUpdateRequest request
            ) {
        return ResponseEntity.ok(subUserService.update(request));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Boolean> delete(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(subUserService.delete(id));
    }
}
