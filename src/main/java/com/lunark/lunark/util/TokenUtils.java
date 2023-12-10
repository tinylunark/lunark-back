package com.lunark.lunark.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class TokenUtils {
    @Value("lunark")
    private String APP_NAME;

    @Value("tinysecret")
    public String SECRET;

    @Value("1800000")
    private int EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    private static final String AUDIENCE_WEB = "web";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String username) {
        return Jwts.builder()
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET)
                .compact();
    }

    public String generateAudience() {
        /*
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (audience.isTablet()) {
            audience = AUDIENCE_TABLET;
        } lese if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        */
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception excetpion) {
            claims = null;
        }
        return claims;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception exception) {
            audience = null;
        }
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims cLaims = this.getAllClaimsFromToken(token);
            expiration = cLaims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }
}