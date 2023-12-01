package cl.ucn.codecrafters.auth.application;

import cl.ucn.codecrafters.auth.domain.AuthRequest;
import cl.ucn.codecrafters.auth.domain.AuthResponse;
import cl.ucn.codecrafters.auth.domain.RegisterRequest;
import cl.ucn.codecrafters.token.Token;
import cl.ucn.codecrafters.token.ITokenRepository;
import cl.ucn.codecrafters.token.TokenType;
import cl.ucn.codecrafters.user.domain.Role;
import cl.ucn.codecrafters.user.domain.User;
import cl.ucn.codecrafters.user.domain.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = this.jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .token(jwtToken)
                .userName(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getDni())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {

        if (this.userRepository.existsUserByEmail(request.getEmail())){
            return null;
        }

        var createdUser = User.builder()
                .dni(request.getDni())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .nationality(request.getNationality())
                .birthDate(request.getBirthDate())
                .role(Role.CLIENT)
                .build();

        var user = this.userRepository.save(createdUser);
        var jwtToken = this.jwtService.generateToken(createdUser);

        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .token(jwtToken)
                .userName(user.getEmail())
                .role(user.getRole().name())
                .userId(user.getDni())
                .build();
    }

    private void revokeAllUserTokens(User user){

        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if (validUserTokens.isEmpty()){
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(Boolean.TRUE);
            token.setRevoked(Boolean.TRUE);
        });

        tokenRepository.saveAll(validUserTokens);

    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(Boolean.FALSE)
                .revoked(Boolean.FALSE)
                .build();

        this.tokenRepository.save(token);
    }
}
