package dev.pavelsgarklavs.netflix_clone.dtos.responses;

import dev.pavelsgarklavs.netflix_clone.database.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse extends BaseResponse {
  private UUID id;
  private String firstname;
  private String lastname;
  private String email;
  private Role role;
  private List<SubUserResponse> subUsers;
}
