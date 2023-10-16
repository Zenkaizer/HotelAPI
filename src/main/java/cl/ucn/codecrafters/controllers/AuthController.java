package cl.ucn.codecrafters.controllers;

import cl.ucn.codecrafters.authentication.AuthResponse;
import cl.ucn.codecrafters.authentication.LoginRequest;
import cl.ucn.codecrafters.authentication.RegisterRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AuthResponse());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) throws Exception {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new AuthResponse());
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
