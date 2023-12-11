package cl.ucn.codecrafters.account.infraestructure;

import cl.ucn.codecrafters.account.application.AccountService;
import cl.ucn.codecrafters.account.domain.*;
import cl.ucn.codecrafters.user.application.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private IUserService userService;

    /**
     * Login controller. Returns the auth response or in other cases a json error message.
     * @param request All params to log in the user.
     * @return Response with the HTTP Status.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        AuthResponse response;

        try {
            response = accountService.login(request);

        }catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().body("Las credenciales del usuario son incorrectas.");
        }

        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        AuthResponse response = this.accountService.register(request);

        if (response == null){
            return ResponseEntity.badRequest().body("Error");
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Method to update the user account (email and password).
     * @param request The request with the new data.
     * @return The response with the new token.
     */
    @PostMapping("/account")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateRequest request) {

        if (this.userService.userEmailExists(request.getEmail())){
            if (!request.getEmail().equals(request.getActualEmail())){
                return ResponseEntity.badRequest().body("El email seleccionado ya existe.");
            }
        }

        if (!request.getPassword().equals(request.getConfirmPassword())){
            return ResponseEntity.badRequest().body("Las contraseñas no coinciden.");
        }

        AuthResponse response = this.accountService.updateUser(request);

        if (response == null){
            return ResponseEntity.badRequest().body("Ha ocurrido un error, intentelo nuevamente.");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody EditRequest request) {


        if (this.userService.userEmailExists(request.getEmail())){

            var user = this.userService.findUserByEmail(request.getEmail());

            if (!Objects.equals(user.getDni(), request.getDni())){
                return ResponseEntity.badRequest().body("El email seleccionado ya existe.");
            }
        }

        AuthResponse response = this.accountService.updateUser(request);

        if (response == null){
            return ResponseEntity.badRequest().body("Ha ocurrido un error, intentelo nuevamente.");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getActualUser(@PathVariable Integer id) {
        try {

            var user = this.accountService.findUserById(id);

            if (user == null){
                return ResponseEntity.badRequest().body("El usuario no existe.");
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ha ocurrido un error, intentelo nuevamente.");
        }
    }

}
