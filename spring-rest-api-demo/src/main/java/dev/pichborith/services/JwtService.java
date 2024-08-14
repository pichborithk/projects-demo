package dev.pichborith.services;

import dev.pichborith.exceptions.UnauthorizedException;
import dev.pichborith.models.User;
import dev.pichborith.repositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
@Service
public class JwtService {

    // Random Encryption key with Base 64, MD5 Hash, Sha256 and Sha512 (Cipher: aes-256-cbc, Length: 64)
    @Value("${SECRET_KEY}")
    public String secretKey;


    public String generatorToken(User user) {
        String username = user.getUsername();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 70000);
        Map<String, Object> extraClaims = new HashMap<>();

        return Jwts.builder()
                   .claims()
                   .add(extraClaims)
                   .subject(username)
                   .issuedAt(currentDate)
                   .expiration(expireDate)
                   .and()
                   .signWith(getSecretKey())
                   .compact();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername())&& !checkExpiration(token));
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean checkExpiration(String token) {
        return getExpiration(token).before(new Date());
    }

    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        Claims claims = (Claims) Jwts.parser()
                                     .verifyWith(getSecretKey())
                                     .build()
                                     .parse(token)
                                     .getPayload();
        return claimsResolver.apply(claims);
    }


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
*/

@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Autowired
    private UserRepo userRepo;

    public String generateToken(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.getId());
        return generateToken(map, user);
    }

    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, 5 * 60 * 1000);
    }

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean validateToken(String token) {
        String username = getUsername(token);
        Integer userId = getUserId(token);
        if (username == null || userId == null) {
            throw new UnauthorizedException("Invalid token");
        }
        User userByUsername = userRepo.findByUsername(username).orElseThrow(
            () -> new UnauthorizedException("Invalid token"));
        User userById = userRepo.findById(userId).orElseThrow(
            () -> new UnauthorizedException("Invalid token"));

        if (!userByUsername.equals(userById) && checkTokenExpiration(token)) {
            throw new UnauthorizedException("Invalid token");
        }
        return true;
    }

    private String buildToken(
        Map<String, Object> extraClaims,
        UserDetails userDetails,
        long expiration
    ) {
        return Jwts
                   .builder()
                   .claims()
                   .add(extraClaims)
                   .subject(userDetails.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(
                       new Date(System.currentTimeMillis() + expiration))
                   .and()
                   .signWith(getSecretKey())
                   .compact();
    }

    private boolean checkTokenExpiration(String token) {
        return getExpiration(token).before(new Date());
    }

    private Integer getUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Integer.class);
    }

    private Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token,
                               Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return (Claims) Jwts
                            .parser()
                            .verifyWith(getSecretKey())
                            .build()
                            .parse(token)
                            .getPayload();
    }

    private SecretKey getSecretKey() {
        String sha512Hash = generateSHA512Hash(secretKey);

        byte[] keyBytes = Decoders.BASE64.decode(sha512Hash);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateSHA512Hash(String input) {
        try {
            // Create a MessageDigest instance for SHA-512
            MessageDigest digest = MessageDigest.getInstance("SHA-512");

            // Convert the input string to bytes and hash it
            byte[] hashBytes = digest.digest(
                input.getBytes(StandardCharsets.UTF_8));

            // Convert the byte array to a hex string
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not found", e);
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(java.lang.String.format("%02x", b));
        }
        return sb.toString();
    }
}