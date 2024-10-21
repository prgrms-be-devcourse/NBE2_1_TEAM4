package edu.example.coffeeproject.service;

import edu.example.coffeeproject.dto.MemberDTO;
import edu.example.coffeeproject.entity.Member;
import edu.example.coffeeproject.repository.MemberRepository;
import jakarta.persistence.Table;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Log4j2
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberDTO read(String mid, String mpw){
       Optional<Member> memberDTO = memberRepository.findById(mid);
        Member member = memberDTO.orElseThrow(() -> new RuntimeException("Member not found"));

        if(!passwordEncoder.matches(mpw, member.getMpw())){
            throw new RuntimeException("Wrong password");
        }
        return new MemberDTO(member);
    }
}
