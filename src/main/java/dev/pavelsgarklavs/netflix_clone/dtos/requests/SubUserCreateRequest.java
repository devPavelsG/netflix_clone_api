package dev.pavelsgarklavs.netflix_clone.dtos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class SubUserCreateRequest {

   private String name;
   private UUID userId;
}
