package dev.pavelsgarklavs.netflix_clone.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@PreAuthorize("hasRole('MANAGER')")
public class ManagementController {
    @GetMapping
    @PreAuthorize("hasAuthority('management:read')")
    public String get() {
        return "GET:: management controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('management:create')")
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('management:update')")
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('management:delete')")
    public String delete() {
        return "DELETE:: management controller";
    }
}
