package dev.pavelsgarklavs.netflix_clone.dtos.responses;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserResponse extends BaseResponse {
  private UUID id;
  private String name;
}
