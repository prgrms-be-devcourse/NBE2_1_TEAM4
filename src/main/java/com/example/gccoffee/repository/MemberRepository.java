package com.example.gccoffee.repository;

import com.example.gccoffee.entity.Member;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Member findByEmail(String emailAddress) {
        TypedQuery<Member> query = em.createQuery(
                "select m from Member m where m.email.address = :email", Member.class
        );
        query.setParameter("email", emailAddress);
        return query.getSingleResult(); // Optional 사용을 고려해볼 수 있습니다.
    }
}

