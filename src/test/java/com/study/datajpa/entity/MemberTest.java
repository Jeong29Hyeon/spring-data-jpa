package com.study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback(false)
@SpringBootTest
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        //given
        // 팀 A,B 2개
        // 멤버 1234    1,2 = 팀 A ,  3,4 = 팀 B
        Team teamA = Team.builder()
                .name("teamA")
                .build();
        Team teamB = Team.builder()
                .name("teamB")
                .build();
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = Member.builder()
                .age(10)
                .userName("member1")
                .build();
        Member member2 = Member.builder()
                .age(10)
                .userName("member1")
                .build();
        Member member3 = Member.builder()
                .age(10)
                .userName("member1")
                .build();
        Member member4 = Member.builder()
                .age(10)
                .userName("member1")
                .build();
        member1.changeTeam(teamA);
        member2.changeTeam(teamA);
        member3.changeTeam(teamB);
        member4.changeTeam(teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();
        //when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }


        //then
     }
}