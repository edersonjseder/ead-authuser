package com.ead.authuser.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtProvider {
    @Value("${ead.auth.jwtSecret}")
    private String jwtSecret;
    @Value("${ead.auth.jwtExpirationMs}")
    private String jwtExpirationMs;

    /** Generate token methods **/
    public String generateToken(Authentication authentication) {
        return generateJwt(new HashMap<>(), authentication);
    }

    private String generateJwt(Map<String, Object> extraClaims, Authentication authentication) {
        var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        extraClaims.put("authorities", getRoles(userDetails.getAuthorities()));
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getId().toString()) // Adiciona o username
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getDate()) // Tempo de duracao do token de 4 horas
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Algoritmo de assinatura do token
                .compact();
    }

    private String getRoles(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

    private Date getDate() {
        return Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + Long.parseLong(jwtExpirationMs)));
    }
    /* Generate token methods */

    /** Validate token methods **/
    public boolean isTokenValid(String token, UserDetailsImpl userDetails) {
        final UUID userId = UUID.fromString(extractSubject(token));
        return (userId.equals(userDetails.getId())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Date extractExpiration(String token) throws SignatureException {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractSubject(String token) throws SignatureException {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) throws SignatureException {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }
    /* Validate token methods */

    /**
     * Method to get signing key for generate and validate token
     * @return hashed key value
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
