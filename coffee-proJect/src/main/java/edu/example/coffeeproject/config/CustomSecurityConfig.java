package edu.example.coffeeproject.config;

import edu.example.coffeeproject.security.JWTCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CustomSecurityConfig {
    private final JWTCheckFilter jwtCheckFilter;

    public CustomSecurityConfig(JWTCheckFilter jwtCheckFilter) {
        this.jwtCheckFilter = jwtCheckFilter;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login.disable())    //로그인 인증 설정 x
                .logout(logout -> logout.disable()) // 로그아웃 x
                .csrf(csrf -> csrf.disable())       // CSRF 는 세션단위 관리 -> x
                .sessionManagement(se -> se.sessionCreationPolicy(SessionCreationPolicy.NEVER)); // 세션 사용 ㅌ
        //JWTCheckFilter 필터 추카
        http.addFilterBefore(jwtCheckFilter
                , UsernamePasswordAuthenticationFilter.class);
        http.cors(cors -> {
            cors.configurationSource(corsConfigurationSource());
        });
        return http.build();
    }
    //CORS Cross Origin Resource Sharing 설정 관련 처리 -------------------------------
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfig = new CorsConfiguration();

        //접근 패턴 - 모든 출처에서의 요청 허락
        corsConfig.setAllowedOriginPatterns(List.of("*"));

        //허용 메서드
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // 허용 헤더
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));

        // 자격 증명 허용 여부
        corsConfig.setAllowCredentials(true);

        // URL 패턴 기반으로 CROS 구성
        UrlBasedCorsConfigurationSource corsSource =
                new UrlBasedCorsConfigurationSource();

        corsSource.registerCorsConfiguration("/**"// 모든 경로 적용
                ,corsConfig );

        return corsSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
