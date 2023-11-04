package dev.pavelsgarklavs.netflix_clone.services;

import dev.pavelsgarklavs.netflix_clone.database.models.SubUser;
import dev.pavelsgarklavs.netflix_clone.database.models.User;
import dev.pavelsgarklavs.netflix_clone.database.repositories.SubUserRepository;
import dev.pavelsgarklavs.netflix_clone.database.repositories.UserRepository;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.SubUserCreateRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.SubUserUpdateRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.SubUserGetAllResponse;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.SubUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubUserService {
    private final SubUserRepository subUserRepository;
    private final UserRepository userRepository;

    public SubUserResponse create(SubUserCreateRequest request) {
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Sub user not found with id " + request.getUserId()));;

        SubUser subUser = SubUser.builder()
                .user(user)
                .name(request.getName())
                .build();

        SubUser dbSubUser = subUserRepository.save(subUser);

        return SubUserResponse
                .builder()
                .name(dbSubUser.getName())
                .build();
    }

    public SubUserGetAllResponse getAll(UUID id) {
        return SubUserGetAllResponse
                .builder()
                .names(subUserRepository.getAllByUserId(id))
                .build();
    }

    public SubUserResponse update(SubUserUpdateRequest request) {
        SubUser subUser = subUserRepository
                .getByUserIdAndSubUserId(request.getUserId(), request.getSubUserId())
                .orElseThrow(() -> new RuntimeException("Sub user not found with id " + request.getUserId()));;

        subUser.setName(request.getNewName());
        SubUser dbUser = subUserRepository.save(subUser);

        return SubUserResponse
                .builder()
                .name(dbUser.getName())
                .build();
    }

    public Boolean delete(UUID id) {
        SubUser subUser = subUserRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Sub user not found with id " + id));

        subUserRepository.delete(subUser);

        return true;
    }
}
