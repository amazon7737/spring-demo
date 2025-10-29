package jpabook.jpashop.api.infrastructure.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.api.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository : 스프링 빈으로 등록, JPA예외를 스프링 기반 예외로 예외 변환
 * @PersistenceContext : 엔티티 매니저 (EntityManager) 주입
 * @PersistenceUnit : 엔티티 매니저 팩토리 (EntityManagerFactory) 주입
 *
 */

@Repository
public class MemberRepository{
    @PersistenceContext
    EntityManager em;


    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }

}
