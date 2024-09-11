package com.example.coffee.security.util.filter;

import com.example.coffee.security.util.JWTUtil;
import com.example.coffee.security.util.auth.CustomUserPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.apache.catalina.users.AbstractUser;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
        }
        String accessToken = authorizationHeader.substring(7);
        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            log.info("--- 토큰 유효성 검증 완료");

            //SecurityContext 처리
            String email = claims.get("email").toString();
            String[] roles = claims.get("roles").toString().split(",");

//            토큰을 이용하여 인증된 정보 저장
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(new CustomUserPrincipal(email), null,
//                            Arrays.stream(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                                    .collect(Collectors.toList()));
//            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);
        }catch (Exception e) {
            e.getMessage();

        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("--- shouldNOtFilter ---");

        // 토큰 경로와 api가 들어가 있지 않는 경로는 필터링 제외
        if(request.getRequestURI().startsWith("/api/v1/token/")) {//토큰 발급 관련 경로는 제외
            return true;
        }
        return !request.getRequestURI().startsWith("/api/");
    }

    public void handleException(HttpServletResponse response, Exception e)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter()
                .println("{\"error\": \"" + e.getMessage() + "\"}");
    }

}
