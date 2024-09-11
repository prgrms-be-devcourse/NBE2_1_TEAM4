package edu.example.coffeeproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {
    // 생성자 인젝셕으로 JWTUtil 객체
    private final JWTUtil jwtUtil;

    @Override       //필터링 적용 x  // 엑세스 토큰 확인 x
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        log.info("--------shouldNotFilter() ");
        log.info("-------requestURI : " + request.getRequestURI());

        if (request.getRequestURI().startsWith("/api/v1/token")) {      // 토큰 발급 관련 경로는 제외
            return true;
        }
        if (!request.getRequestURI().startsWith("/api/")) { // /api/로 시작하지 않는 경로는 제외
            return true;
        }
        return false; // 햔재 모든 경로 필터링
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.info("doFilterInternal()");
        log.info("request: " + request.getRequestURI());

        String headerAuth = request.getHeader("Authorization"); // 토큰이 들어있는곳
        log.info("headerAuth: " + headerAuth);
        log.info("response: " + response);
        //액세스 토큰이 없거나 'Bearer ' 가 아니면 403 예외 발생
        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) {
            handleException(response,
                    new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }

        // 토큰 유효성 검증 ------------------------------
        String accessToken = headerAuth.substring(7);
        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);//"Bearer " 를 제외하고 토큰저장

            log.info("----- 토큰 유효성 검증 완료 ------");

            // SecurityContext 처리 ---------------------------------------
            String mid = claims.get("mid").toString();
            String[] roles = claims.get("role").toString().split(",");

            // 토큰을 이용하여 인증된 정보 저장

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            new CustomUserPrincipal(mid),
                            null,
                            Arrays.stream(roles).map(role ->
                                            new SimpleGrantedAuthority("ROLE_" + role))
                                    .collect(Collectors.toList())
                    );

            //SecurityContext에 인증/인가 정보 저장
            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);

            filterChain.doFilter(request, response);// 검증 결과 문제가 없는 경우
        } catch (Exception e) {
            handleException(response, e);       // 예외가 발생한 경우
        }

    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\" : \"" + e.getMessage() + "\"}");


    }
}