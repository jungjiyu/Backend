package com.likelionkit.board.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String BEARER_PREFIX = "Bearer";
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);
        if(token !=null){
            // 토큰 검증 (만료되었거나 변조되지 않았는지)
            String username =tokenProvider.validateToken(token);
            if(username != null){
                // 인증되지 않은 Authentication 객체를 생성
                    // 이게 db 까지 접근해서 usernamedetails 이런거 다 쓰는거
                    // 인증되지 않은 토큰 줄테니까 인증되었는지 검사ㅎ래라
                try {
                    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, "")); // 비밀번호는 딱히 필요 없어서 빈 문자열로 넣음
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }catch (RuntimeException e){
                    log.error(e.getMessage() + ": JWT로 부터 인증 정보를 만드는데 실패했습니다");
                }
            }
        }
        filterChain.doFilter(request,response); // 검증 필요가 없은 ㅣ넘겨버린다
    }

    private String resolveToken(HttpServletRequest request){
        String bearToken = request.getHeader(HttpHeaders.AUTHORIZATION); // 헤더에서 꺼낸다
        if(bearToken != null && bearToken.startsWith( BEARER_PREFIX )){
            return bearToken.substring( BEARER_PREFIX .length());
        }
        return null;
    }
}
