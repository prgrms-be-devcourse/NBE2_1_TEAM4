package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
