package tic4303.miniproject.server.configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    // jwt encoder and decoder
    // https://jwt.io/
    // header
    // payload = claims, where user data can be extracted
    // signature = verify the sender, ensure message not changed along the way

    // @Value("${SECRET_KEY_JWT}") // this is also the api key
    // private String secretKey;
    // 256-bit, HEX - Use online key generator

    private static final String SECRET_KEY = "404D635166546A576E5A7234753777217A25432A462D4A614E645267556B587";
    private static final Integer EXPIRATION = 1000 * 60 * 60 * 24; // 1 day

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    // method to extract a single claim
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    // method to extract all claims
    private Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
    }

    // check validity of the token, valid if username matches and if the token is not expired
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals((userDetails.getUsername())) && !isTokenExpired(jwt));
    }

    // helper function to check if token is expired based on today's date
    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    // helper function to extract the claims expiration date
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    // method to generate token from userDetails only
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // method to generate the token from extraClaims and userDetails
    /* 
    extra claims are included in the token to provide additional information 
    that can be used by the recipient of the token to make more informed decisions 
    about how to handle the authenticated user. These claims are typically added to 
    the token in addition to the standard claims (such as sub, exp, etc.) and provide more 
    context about the authentication process or the user's role and permissions (Authorization).
    */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .claim("role", userDetails.getAuthorities().toString())
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    /*
    signingkey is a secret key that is use to digitally sign the signature of the JWT
    used to verify the sender and ensure message is not change
    signingkey is use with the signing algo to create the signature of the JWT
    generate a signingkey from https://www.allkeysgenerator.com/
    */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
      }

}
