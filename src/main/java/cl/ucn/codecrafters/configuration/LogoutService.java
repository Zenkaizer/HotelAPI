package cl.ucn.codecrafters.configuration;

import cl.ucn.codecrafters.token.ITokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final ITokenRepository tokenRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        jwt = authHeader.substring(7);

        var storedToken = tokenRepository.findByToken(jwt).orElse(null);

        if (storedToken != null){
            storedToken.setExpired(Boolean.TRUE);
            storedToken.setRevoked(Boolean.TRUE);

            tokenRepository.save(storedToken);
        }
    }
}
