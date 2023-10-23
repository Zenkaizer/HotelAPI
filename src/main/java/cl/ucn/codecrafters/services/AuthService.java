package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.authentication.AuthResponse;
import cl.ucn.codecrafters.authentication.LoginRequest;
import cl.ucn.codecrafters.authentication.RegisterRequest;
import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        // Auth the user
        this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserDetails user = this.userRepository.findByEmail(request.getEmail()).orElseThrow();

        // Generate the token
        String token = jwtService.getToken(user);

        // Return the token
        return AuthResponse.builder().token(token).build();

    }

    public AuthResponse register(RegisterRequest request) throws Exception {

        try {

            if (this.userRepository.existsUserByEmail(request.getEmail())){
                throw new Exception("[!] An User with that email is already registered [!]");
            }

            User createdUser = User.builder()
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
                    .token(this.jwtService.getToken(createdUser))
                    .build();
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
