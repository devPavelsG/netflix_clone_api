package dev.pavelsgarklavs.netflix_clone.services;

import dev.pavelsgarklavs.netflix_clone.database.repositories.SubUserRepository;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.ChangePasswordRequest;
import dev.pavelsgarklavs.netflix_clone.database.models.User;
import dev.pavelsgarklavs.netflix_clone.database.repositories.UserRepository;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.SubUserResponse;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public UserResponse me(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        List<SubUserResponse> subUserResponses = user.getSubUsers()
                .stream()
                .map(element -> SubUserResponse.
                        builder().
                        name(element.getName()).
                        id(element.getId()).
                        build()
                )
                .toList();

        return UserResponse.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .subUsers(subUserResponses)
                .build();
    }
}
