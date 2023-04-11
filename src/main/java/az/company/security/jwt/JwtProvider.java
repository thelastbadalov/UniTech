package az.company.security.jwt;

import az.company.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("${authentication.jwt.expiration-in-ms}")
    private Long JWT_EXPIRES_MIN;

    private static final String JWT_TOKEN_PREFIX = "Bearer";

    private static final String JWT_HEADER_STRING = "Authorization";

    private final PublicKey jwtPublicKey;

    private final PrivateKey jwtPrivateKey;

    public JwtProvider(@Value("${authentication.jwt.private-key}") String jwtPrivateKeyStr,
                       @Value("${authentication.jwt.public-key}") String jwtPublicKeyStr) {
        KeyFactory keyFactory = getKeyFactory();

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            PKCS8EncodedKeySpec privateKeySpace = new PKCS8EncodedKeySpec(decoder.decode(jwtPrivateKeyStr.getBytes()));
            X509EncodedKeySpec publicKeySpace = new X509EncodedKeySpec(decoder.decode(jwtPublicKeyStr.getBytes()));
            jwtPrivateKey = keyFactory.generatePrivate(privateKeySpace);
            jwtPublicKey = keyFactory.generatePublic(publicKeySpace);
        } catch (Exception ex) {
            throw new RuntimeException("Invalid key spaceifaction", ex);
        }
    }


    public String generateToken(UserPrincipal authentication) {
        String authorities = authentication.getAuthorities().stream().
                map(GrantedAuthority::getAuthority).collect(Collectors.joining());
        return Jwts.builder()
                .setSubject(authentication.getUsername())
                .claim("userId", authentication.getId())
                .claim("roles", authorities)
                .setExpiration(new Date(System.currentTimeMillis()+JWT_EXPIRES_MIN))
                .signWith(jwtPrivateKey, SignatureAlgorithm.RS512)
                .compact();

    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null) {
            return null;
        }
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtPublicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        String userId = claims.get("userId", String.class);
        List<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails userDetails = new UserPrincipal(userId, username, null);
        return username != null ? new UsernamePasswordAuthenticationToken(userDetails, null, authorities) : null;
    }

    public boolean isTokenValid(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token == null)
            return false;

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtPublicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration().before(new Date())) {
            return false;
        }
        return true;
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER_STRING);

        if (bearerToken != null && bearerToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private KeyFactory getKeyFactory() {
        try {
            return KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException exception) {
            throw new RuntimeException("Unknown Key geneartion algortim", exception);
        }
    }
}
