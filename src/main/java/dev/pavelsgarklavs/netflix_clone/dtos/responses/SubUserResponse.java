package dev.pavelsgarklavs.netflix_clone.dtos.responses;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubUserResponse extends BaseResponse {
  private String name;
}
