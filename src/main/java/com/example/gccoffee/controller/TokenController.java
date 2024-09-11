package com.example.gccoffee.controller;

import com.example.gccoffee.dto.MemberDTO;
import com.example.gccoffee.security.util.JWTUtil;
import com.example.gccoffee.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@Log4j2
@Tag(name = "회원", description = "토큰 생성 / 검증")
public class TokenController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    //토큰 생성
    @PostMapping("/make")
    @Operation(summary = "토큰 생성", description = "회원의 토큰을 생성하는 API")
    public ResponseEntity<Map<String, Object>> makeToken(@RequestBody MemberDTO memberDTO) {
        log.info("Making token");

        MemberDTO foundMemberDTO = memberService.read(memberDTO.getMid(), memberDTO.getMpw());

        Map<String, Object> payloadMap = foundMemberDTO.getPayload();
        String accessToken = jwtUtil.createToken(payloadMap, 600);
        String refreshToken = jwtUtil.createToken(Map.of("mid", foundMemberDTO.getMid()), 60 * 24 * 7);

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

    public ResponseEntity<Map<String, String>> handleException(String message, int status) {
        return ResponseEntity.status(status).body(Map.of("error", message));
    }

    //리프레시 토큰 검증
    @PostMapping("/refresh")
    @Operation(summary = "리프레시 토큰 검증", description = "회원의 리프레시 토큰을 검중하는 API")
    public ResponseEntity<Map<String, String>> refreshToken(
                                                            @RequestHeader("Authorization") String headerAuth,
                                                            @RequestParam("refreshToken") String refreshToken,
                                                            @RequestParam("mid") String mid) {
        log.info("Refreshing token");

        if (headerAuth == null || !headerAuth.startsWith("Bearer ")) return handleException("액세스 토큰이 없습니다.", 400);

        if (refreshToken == null || refreshToken.isEmpty()) return handleException("리프레시 토큰이 없습니다.", 400);

        if (mid == null || mid.isEmpty()) return handleException("mid가 없습니다.", 400);

        try {
            String accessToken = headerAuth.substring(7);

            Map<String, Object> claims = jwtUtil.validateToken(accessToken);

        } catch (ExpiredJwtException e) {
            try {
                return ResponseEntity.ok(makeNewToken(mid, refreshToken));
            } catch(ExpiredJwtException ee) {
                return handleException("리프레시 토큰 기간 만료 : " + ee.getMessage(), 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return handleException("리프레시 토큰 검증 예외 : " + e.getMessage(), 400);
        }

        return null;
    }

    //새로운 토큰 생성
    public Map<String, String> makeNewToken(String mid, String refreshToken) {
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        log.info("Making new token");

        if (!claims.get("mid").equals(mid)) throw new RuntimeException("INVALID REFRESH mid");

        MemberDTO foundMemberDTO = memberService.read(mid);

        Map<String, Object> payloadMap = foundMemberDTO.getPayload();

        String newAccessToken = jwtUtil.createToken(payloadMap, 10);
        String newRefreshToken = jwtUtil.createToken(Map.of("mid", foundMemberDTO.getMid()), 60 * 24 * 7);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken, "mid", mid);
    }

    //상태 코드 400과 메시지 전송
    public ResponseEntity<Map<String, String>> sendResponse(String message) {
        return new ResponseEntity<>(Map.of("error", message), HttpStatus.BAD_REQUEST);
    }

    //리프레시 토큰 검증 처리
    @PostMapping("/refreshVerify")
    @Operation(summary = "리프레시 토큰 검증 처리", description = "회원의 리프레시 토큰 검증을 처리하는 API")
    public ResponseEntity<Map<String, String>> refreshVerify(
                                                            @RequestHeader("Authorization") String headerAuth,
                                                            @RequestParam("refreshToken") String refreshToken,
                                                            @RequestParam("mid") String mid){
        log.info("Refreshing token");

        if(!headerAuth.startsWith("Bearer ")) return sendResponse("액세스 토큰이 없습니다.");
        if(refreshToken.isEmpty()) return sendResponse("리프레시 토큰이 없습니다.");
        if(mid.isEmpty()) return sendResponse("아이디가 없습니다.");

        try {
            String accessToken = headerAuth.substring(7);
            Map<String, Object> claims = jwtUtil.validateToken(accessToken);

            return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken, "mid", mid));
        } catch (ExpiredJwtException e) {
            try{
                Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

                if(!claims.get("mid").equals(mid)) return sendResponse("INVALID REFRESH TOKEN mid");

                MemberDTO foundMemberDTO = memberService.read(mid);
                Map<String, Object> payloadMap = foundMemberDTO.getPayload();
                String newAccessToken = jwtUtil.createToken(payloadMap, 1);
                String newRefreshToken = jwtUtil.createToken(Map.of("mid", mid), 3);

                return ResponseEntity.ok(Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken, "mid", mid));

            } catch (ExpiredJwtException ee) {
                log.error("Refresh token expired");
            }
        } catch(Exception e) {
            return sendResponse("리프레시 토큰 처리 예외 : " + e.getMessage());
        }
        return null;
    }
}
