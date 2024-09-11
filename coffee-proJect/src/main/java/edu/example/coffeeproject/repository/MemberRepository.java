package edu.example.coffeeproject.repository;

import edu.example.coffeeproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
