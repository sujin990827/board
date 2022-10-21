package com.study.board.repository;

import com.study.board.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 스프링이 자동주입한다.

    public void save(Member member){
        em.persist(member);
    }

    //단건 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //리스트 조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //이름으로 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
