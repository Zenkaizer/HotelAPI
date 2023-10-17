package cl.ucn.codecrafters.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    //private static final String SECRET_KEY = "5RDCG3V19PW2CYSK7MLXH1JV6TU4WZZQ";

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private SecretKey secretKey = Keys.secretKeyFor(signatureAlgorithm);

    public String getToken(UserDetails user) {

        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, UserDetails user){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getKey(){

        byte[] keyBytes = secretKey.getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
