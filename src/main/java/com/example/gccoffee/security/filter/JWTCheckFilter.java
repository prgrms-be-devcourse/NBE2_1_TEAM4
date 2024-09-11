package com.example.gccoffee.security.filter;

import com.example.gccoffee.security.auth.CustomUserPrincipal;
import com.example.gccoffee.security.util.JWTUtil;
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
    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("JWTCheckFilter shouldNotFilter");

        if(request.getRequestURI().startsWith("/api/v1/token/")) return true;

        if(!request.getRequestURI().startsWith("/api/")) return true;

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JWTCheckFilter doFilterInternal");

        String headerAuth = request.getHeader("Authorization");

        if( headerAuth == null || !headerAuth.startsWith("Bearer ") ) {
            handleException(response, new Exception("ACCESS TOKEN NOT FOUND"));
            return;
        }

        String accessToken = headerAuth.substring(7);

        try {
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);
            String mid = claims.get("mid").toString();
            String[] roles = claims.get("role").toString().split(",");

            UsernamePasswordAuthenticationToken authToken
                    = new UsernamePasswordAuthenticationToken(
                    new CustomUserPrincipal(mid),
                    null,
                    Arrays.stream(roles)
                            .map( role
                                    -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch(Exception e) {
            handleException(response, e);
        }
    }

    public void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().println("{\"error\": \"" + e.getMessage() + "\"}");
    }
}
