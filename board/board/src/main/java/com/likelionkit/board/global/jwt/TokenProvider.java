package com.likelionkit.board.global.jwt;

import com.likelionkit.board.domain.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider implements AuthenticationProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;


    // pw 는 따로 담지 않는다.
    public String createToken(String username , UserRole role){
        Claims claims = Jwts.claims().setSubject("ACCESS_TOKEN");
        claims.put("username",username); //유니크하니까
        claims.put("role",role);
        
        Date now = new Date(System.currentTimeMillis());
        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 300*1000L)) // 5분동안 유효
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256) // 시크릿키로 암호화
                .compact();
    }

    public String validateToken(String token) {
        try{
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token).getBody(); // getBody( ) 가 claim 을 받아온다.

            return (String)claims.get("username");
        }catch(ExpiredJwtException e){
            log.error(e.toString()+" jwt 가 만료됬음");
            // 재발급 로직 추가가능
        }catch (Exception e){
            log.error(e.toString()+" 토큰이 유효하지 않음(변조)");
        }

        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // userdetailservice를 통해 실제 데이터베이스에서 해당 user 가 존재하는지 검사 및 가져오기
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 인증토큰클래스가 맞냐 검사하는거
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
