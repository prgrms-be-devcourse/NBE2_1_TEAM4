package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원 등록 테스트
    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 9).forEach(i -> {
            Member member = Member.builder().mid("user" + i)
                                            .mpw(passwordEncoder.encode("1111"))
                                            .mname("USER" + i)
                                            .email("user" + i + "@aaa.com")
                                            .role(i <= 5 ? "USER" : "ADMIN")
                                            .build();

            Member savedMember = memberRepository.save(member);

            assertNotNull(savedMember);
            if(i <= 5) assertEquals("USER", savedMember.getRole());
            else assertEquals("ADMIN", savedMember.getRole());
        });
    }

    //회원 조회 테스트
    @Test
    public void testFindById() {
        String mid = "user1";
        Member foundMember = memberRepository.findById(mid).orElse(null);

        assertNotNull(foundMember);
        assertEquals(mid, foundMember.getMid());
    }

    //회원 수정 테스트
    @Test
    @Transactional
    @Commit
    public void testUpdateTransactional() {
        String mid = "user1";
        Member foundMember = memberRepository.findById(mid).orElse(null);

        assertNotNull(foundMember);

        foundMember.changeEmail("bbb@bbb.com");
        foundMember.changePassword(passwordEncoder.encode("2222"));

        assertEquals("bbb@bbb.com", foundMember.getEmail());
    }

    //회원 삭제 테스트
    @Test
    @Commit
    @Transactional
    public void testDeleteTransactional() {
        String mid = "user1";

        memberRepository.deleteById(mid);

        assertTrue(memberRepository.findById(mid).isEmpty());
    }
}
