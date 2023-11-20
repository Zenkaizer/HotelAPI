package cl.ucn.codecrafters.auth.infraestructure;

import cl.ucn.codecrafters.auth.application.AuthService;
import cl.ucn.codecrafters.auth.domain.AuthRequest;
import cl.ucn.codecrafters.auth.domain.AuthResponse;
import cl.ucn.codecrafters.auth.domain.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Login controller. Returns the auth response or in other cases a json error message.
     * @param request All params to log in the user.
     * @return Response with the HTTP Status.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {

        AuthResponse response = this.authService.register(request);

        if (response == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(authService.register(request));
    }

}
