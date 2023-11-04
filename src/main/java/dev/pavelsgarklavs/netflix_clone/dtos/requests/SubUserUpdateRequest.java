package dev.pavelsgarklavs.netflix_clone.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SubUserUpdateRequest {
   private UUID userId;
   private UUID subUserId;
   private String newName;
}
