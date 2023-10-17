package cl.ucn.codecrafters.controllers;

import cl.ucn.codecrafters.authentication.AuthResponse;
import cl.ucn.codecrafters.authentication.LoginRequest;
import cl.ucn.codecrafters.authentication.RegisterRequest;
import cl.ucn.codecrafters.services.AuthService;
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
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.authService.login(request));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    /**
     * Register controller. Returns the auth response or in other cases a json error message.
     * @param request All params to create a new user.
     * @return Response with the HTTP Status.
     */
    @PostMapping(value = "register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.authService.register(request));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

}
