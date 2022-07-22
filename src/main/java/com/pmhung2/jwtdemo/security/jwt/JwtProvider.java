package com.pmhung2.jwtdemo.security.jwt;

import com.pmhung2.jwtdemo.security.userPrincipal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class.getName());
    private String jwtSecret = "pmhung2@cmcglobal.vn";
    private int jwtExpiration = 86400;

    public String createToken(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername()).setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {}", e); //ném ra thông báo có ngoại lệ là các đối tượng e(e là tên các ngoại lệ) trong dấu {}
        } catch (MalformedJwtException e) {
            logger.error("the token invalid format -> Message:{}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message:{}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token -> Message:{}", e);
        } catch (IllegalArgumentException e) {
            logger.error("Jwt claims String is empty -> Message:{}", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        String userName = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }
}
