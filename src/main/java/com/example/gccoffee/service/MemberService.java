package com.example.gccoffee.service;

import com.example.gccoffee.dto.member.MemberResponseDTO;
import com.example.gccoffee.entity.Member;
import com.example.gccoffee.exception.MemberException;
import com.example.gccoffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 조회 - 아이디와 비밀번호 이용
    public MemberResponseDTO read(String mid, String mpw) {
        Optional<Member> foundMember = memberRepository.findById(mid);
        Member member = foundMember.orElseThrow(MemberException.BAD_CREDENTIALS::get);

        if (!passwordEncoder.matches(mpw, member.getMpw())) {
            throw MemberException.BAD_CREDENTIALS.get();
        }

        return new MemberResponseDTO(member);
    }

    //회원 조회 - 아이디 이용
    public MemberResponseDTO read(String mid) {
        Optional<Member> foundMember = memberRepository.findById(mid);
        Member member = foundMember.orElseThrow(MemberException.BAD_CREDENTIALS::get);

        return new MemberResponseDTO(member);
    }
}
