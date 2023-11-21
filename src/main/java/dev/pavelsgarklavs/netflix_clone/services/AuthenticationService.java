package dev.pavelsgarklavs.netflix_clone.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pavelsgarklavs.netflix_clone.database.models.SubUser;
import dev.pavelsgarklavs.netflix_clone.database.repositories.SubUserRepository;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.AuthenticationRequest;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.AuthenticationResponse;
import dev.pavelsgarklavs.netflix_clone.dtos.requests.RegisterRequest;
import dev.pavelsgarklavs.netflix_clone.config.JwtService;
import dev.pavelsgarklavs.netflix_clone.database.models.Token;
import dev.pavelsgarklavs.netflix_clone.database.repositories.TokenRepository;
import dev.pavelsgarklavs.netflix_clone.database.enums.TokenType;
import dev.pavelsgarklavs.netflix_clone.database.models.User;
import dev.pavelsgarklavs.netflix_clone.database.repositories.UserRepository;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.BaseResponse;
import dev.pavelsgarklavs.netflix_clone.dtos.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final SubUserRepository subUserRepository;

  public BaseResponse register(RegisterRequest request) {
    if (repository.existsByEmail(request.getEmail())) {
      return new ErrorResponse("validation.email_already_exists");
    }

    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = repository.save(user);

    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole());

    var jwtToken = jwtService.generateToken(extraClaims, user);
    var refreshToken = jwtService.generateRefreshToken(extraClaims, user);

    saveUserToken(savedUser, jwtToken, refreshToken);
    saveInitialSubUser(savedUser, request.getFirstname());
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();

    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole());

    var jwtToken = jwtService.generateToken(extraClaims, user);
    var refreshToken = jwtService.generateRefreshToken(extraClaims, user);

    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken, refreshToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public Optional<User> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  private void saveUserToken(User user, String jwtToken, String jwtRefreshToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .refreshToken(jwtRefreshToken)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole());

        var accessToken = jwtService.generateToken(extraClaims, user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }

  private void saveInitialSubUser(User user, String name) {
    var subUser = SubUser.builder()
            .user(user)
            .name(name)
            .build();
    subUserRepository.save(subUser);
  }
}
