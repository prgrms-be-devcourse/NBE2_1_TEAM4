package edu.example.coffeeproject.controller;

import edu.example.coffeeproject.dto.MemberDTO;
import edu.example.coffeeproject.security.JWTUtil;
import edu.example.coffeeproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/token")
@Log4j2
public class TokenController {
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/make")
    public ResponseEntity<Map<String, Object>> makeToken(@RequestBody MemberDTO memberDTO) {// POST 로 /api/v1/token/make 요청을 처리하는 makeToken 메서드
        log.info("Making token");

        //사용자 정보 가져오기
        MemberDTO foundMemberDTO = memberService.read(memberDTO.getMid(), memberDTO.getMpw());
        log.info("-------------" + foundMemberDTO);

        //토큰 생성
        Map<String, Object> payloadMap = foundMemberDTO.getPayLoad();

//        String accessToken = jwtUtil.createToken(payloadMap, 1); // 1분 유효
//        String refreshToken = jwtUtil.createToken(Map.of("mid", foundMemberDTO.getMid()),
//                60 * 24 * 7);       // 7일 유효

        // 1분 유효
        String refreshToken = jwtUtil.createToken(Map.of("mid", foundMemberDTO.getMid()),
                60 * 24 * 7);
        String accessToken = jwtUtil.createToken(payloadMap, 1000);
        log.info("-------------accessToken" + accessToken);
        log.info("-------------refreshToken" + refreshToken);

        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }
}
