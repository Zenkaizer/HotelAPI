package cl.ucn.codecrafters.account.application;

import cl.ucn.codecrafters.account.domain.LoginRequest;
import cl.ucn.codecrafters.account.domain.AuthResponse;
import cl.ucn.codecrafters.account.domain.RegisterRequest;
import cl.ucn.codecrafters.account.domain.UpdateRequest;
import cl.ucn.codecrafters.token.Token;
import cl.ucn.codecrafters.token.ITokenRepository;
import cl.ucn.codecrafters.token.TokenType;
import cl.ucn.codecrafters.user.domain.entities.Role;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.user.domain.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AccountService {

    private final IUserRepository userRepository;
    private final ITokenRepository tokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) throws AuthenticationException {

        try{

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

        }catch (AuthenticationException ex){
            throw ex;
        }

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = this.jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
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
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    /**
     * Method that
     * @param request
     * @return
     */
    public AuthResponse updateUser(UpdateRequest request) {

        var user = this.userRepository.findByEmail(request.getActualEmail()).orElseThrow();

        if (!request.getActualEmail().equals(request.getEmail())){
            user.setEmail(request.getEmail());
        }

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        var updatedUser = this.userRepository.save(user);
        var jwtToken = this.jwtService.generateToken(updatedUser);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    /**
     * This method revoke all user's tokens.
     * @param user User to remove tokens.
     */
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

    /**
     * This method save the user token.
     * @param user User to save the token.
     * @param jwtToken JWT to save.
     */
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
