package dev.pavelsgarklavs.netflix_clone.database.models;

import dev.pavelsgarklavs.netflix_clone.database.enums.TokenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TOKENS")
public class Token extends BaseEntity {

  @Column(name = "TOKEN")
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "TOKEN_TYPE")
  private TokenType tokenType = TokenType.BEARER;

  @Column(name = "REFRESH_TOKEN", unique = true)
  private String refreshToken;

  @Column(name = "REVOKED")
  private boolean revoked;

  @Column(name = "EXPIRED")
  private boolean expired;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
  private User user;
}

