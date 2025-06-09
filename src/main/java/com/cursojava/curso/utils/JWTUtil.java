package com.cursojava.curso.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.secret}")
    private String keyBase64;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    @PostConstruct
    public void validateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(keyBase64);
        if (keyBytes.length < 32) {
            log.error("La clave JWT debe tener al menos 256 bits (32 bytes) para HS256.");
            throw new IllegalArgumentException("Clave secreta JWT débil");
        }
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyBase64));
    }

    /**
     * Crea un nuevo token JWT firmado.
     *
     * @param id      identificador único del token
     * @param subject sujeto o nombre de usuario
     * @return el token JWT firmado como cadena compacta
     */
    public String create(String id, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256);

        if (ttlMillis >= 0) {
            Date exp = new Date(nowMillis + ttlMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    /**
     * Extrae el "subject" (usuario) de un token JWT válido.
     *
     * @param jwt el token JWT
     * @return el sujeto (subject) o null si es inválido
     */
    public String getSubjectFromToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            log.warn("JWT inválido al obtener subject: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extrae el ID del token JWT.
     *
     * @param jwt el token JWT
     * @return el ID del token o null si es inválido
     * */
    public String getIdFromToken(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims.getId();
        } catch (JwtException e) {
            log.warn("JWT inválido al obtener ID: {}", e.getMessage());
            return null;
        }
    }

}
