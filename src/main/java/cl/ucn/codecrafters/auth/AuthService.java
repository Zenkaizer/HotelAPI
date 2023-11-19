package cl.ucn.codecrafters.auth;

import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.repositories.IUserRepository;
import cl.ucn.codecrafters.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;

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

        return AuthResponse.builder()
                .token(this.jwtService.generateToken(user))
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

        this.userRepository.save(createdUser);

        return AuthResponse.builder()
                .token(this.jwtService.generateToken(createdUser))
                .build();


    }
}
