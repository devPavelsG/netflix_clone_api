package dev.pavelsgarklavs.netflix_clone.database.seeders;

import dev.pavelsgarklavs.netflix_clone.database.enums.Role;
import dev.pavelsgarklavs.netflix_clone.database.models.User;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import dev.pavelsgarklavs.netflix_clone.services.AuthenticationService;

import java.util.Optional;

@Component
public class UserSeeder {

    private final AuthenticationService authService;

    public UserSeeder(AuthenticationService authService) {
        this.authService = authService;
    }

    @Bean
    public CommandLineRunner seedData() {
        return args -> {
            Optional<User> admin = authService.findByEmail("admin@mail.com");
            Optional<User> manager = authService.findByEmail("manager@mail.com");

            // Seed admin user
            if (admin.isEmpty()) {
                RegisterRequest adminRequest = RegisterRequest.builder()
                        .firstname("Admin")
                        .lastname("Admin")
                        .email("admin@mail.com")
                        .password("password")
                        .role(Role.ADMIN)
                        .build();
                authService.register(adminRequest);
            }

            // Seed manager user
            if (manager.isEmpty()) {
                RegisterRequest managerRequest = RegisterRequest.builder()
                        .firstname("Manager")
                        .lastname("Manager")
                        .email("manager@mail.com")
                        .password("password")
                        .role(Role.MANAGER)
                        .build();
                authService.register(managerRequest);
            }
        };
    }
}
