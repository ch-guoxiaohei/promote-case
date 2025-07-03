package com.guoxiaohei.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Description:
 *
 * @author guoyupeng [2019/3/25]
 */
public final class JwtGenerate {

    private Logger log = LoggerFactory.getLogger(JwtGenerate.class);

    private JwtGenerate() {
    }

    private static final JwtGenerate JWT_GENERATE = new JwtGenerate();

    public static JwtGenerate getInstance() {
        return JWT_GENERATE;
    }

    public String createJWT(String name, String issuer, long TTLMillis,
                            String base64Security) {

        SecretKey key = Keys.hmacShaKeyFor(
                DatatypeConverter.parseBase64Binary(base64Security)
        );
        return Jwts.builder()
                .header().type("JWT").and()
                .subject(name)
                .issuer(issuer)
                .claim("unique_name", name)
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .expiration(TTLMillis >= 0 ?
                        new Date(System.currentTimeMillis() + TTLMillis) : null)
                .compact();
    }

    public Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(
                    DatatypeConverter.parseBase64Binary(base64Security)
            );
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jsonWebToken)
                    .getPayload();

        } catch (Exception ex) {
            log.error("JWT 解析失败: {}", ex.getMessage());
            return null;
        }
    }
}
