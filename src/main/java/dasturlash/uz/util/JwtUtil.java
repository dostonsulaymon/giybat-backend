package dasturlash.uz.util;

import dasturlash.uz.dto.JwtDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private static int tokenLiveTime; // 1-day
    private static int refreshTokenLiveTime; // 30 days
    private static String secretKey;

    @Value("${token.live-time}")
    public void setTokenLiveTime(int tokenLiveTime) {
        JwtUtil.tokenLiveTime = tokenLiveTime;
    }

    @Value("${refresh-token.live-time}")
    public void setRefreshTokenLiveTime(int refreshTokenLiveTime) {
        JwtUtil.refreshTokenLiveTime = refreshTokenLiveTime;
    }

    @Value("${secret-key}")
    public void setSecretKey(String secretKey) {
        JwtUtil.secretKey = secretKey;
    }

//    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
//    private static final int refreshTokenLiveTime = 1000 * 3600 * 24 * 30; // 30 days
//    private static final String secretKey = "VGhlIHdvcmxkIGlzIGEgbGllLCBidXQgaXQgaXMgcmVhbGx5IHRydWU=";

    public static String generateRefreshToken(String username) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("type", "refresh");

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenLiveTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static String encode(String username, String role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        String role = claims.containsKey("role") ? (String) claims.get("role") : null;
        String type = (String) claims.get("type");

        return new JwtDTO(username, role, type);
    }

    private static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}