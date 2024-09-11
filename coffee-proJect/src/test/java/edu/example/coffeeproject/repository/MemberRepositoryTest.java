package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,30).forEach(i -> {
            Member member = Member.builder()
                    .mid("user"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .role( i <= 20 ? "USER" : "ADMIN")
                    .build();

            memberRepository.save(member)
        });
    }
}