package cl.ucn.codecrafters.services;

import cl.ucn.codecrafters.authentication.AuthResponse;
import cl.ucn.codecrafters.authentication.LoginRequest;
import cl.ucn.codecrafters.authentication.RegisterRequest;
import cl.ucn.codecrafters.entities.Role;
import cl.ucn.codecrafters.entities.User;
import cl.ucn.codecrafters.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {

        User user = User.builder()
                .dni(request.getDni())
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .nationality(request.getNationality())
                .birthDate(request.getBirthDate())
                .role(Role.CLIENT)
                .build();

        this.userRepository.save(user);

        return AuthResponse.builder()
                .token(this.jwtService.getToken(user))
                .build();

    }
}
