package dev.pavelsgarklavs.netflix_clone.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends BaseResponse {

  private String accessToken;

  private String refreshToken;
}
