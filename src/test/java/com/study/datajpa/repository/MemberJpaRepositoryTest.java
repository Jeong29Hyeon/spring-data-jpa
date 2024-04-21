package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false) //테스트에서 롤백 안하고 싶으면 이 설정 사용
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.builder()
                .userName("memberA")
                .build();
        Member savedMember = memberJpaRepository.save(member);

        //when
        Member findMember = memberJpaRepository.find(savedMember.getId());

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUserName()).isEqualTo(member.getUserName());
        assertThat(findMember).isEqualTo(member);
     }

     @Test
     public void basicCRUD() throws Exception {
         //given
         Member member1 = Member.builder()
                 .userName("member1")
                 .build();
         Member member2 = Member.builder()
                 .userName("member2")
                 .build();
         memberJpaRepository.save(member1);
         memberJpaRepository.save(member2);

         //단건 조회
         Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
         Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

         assertThat(findMember1).isEqualTo(member1);
         assertThat(findMember2).isEqualTo(member2);

         //리스트 조회 검증
         System.out.println("-----------------------------------------------------");
         List<Member> all = memberJpaRepository.findAll();
         System.out.println("-----------------------------------------------------");
         assertThat(all.size()).isEqualTo(2);

         //카운트 검증
         System.out.println("-----------------------------------------------------");
         long count = memberJpaRepository.count();
         assertThat(count).isEqualTo(2);

         //삭제 검증
         memberJpaRepository.delete(member1);
         memberJpaRepository.delete(member2);
         long deletedCount = memberJpaRepository.count();
         System.out.println("-----------------------------------------------------");
         assertThat(deletedCount).isEqualTo(0);
     }

     @Test
     public void testFindByUserNameAndAgeGreaterThan() throws Exception {
         //given
         Member member1 = Member.builder()
                 .userName("AAA")
                 .age(15)
                 .build();
         Member member2 = Member.builder()
                 .userName("AAA")
                 .age(20)
                 .build();
         memberJpaRepository.save(member1);
         memberJpaRepository.save(member2);
         //when
         List<Member> list = memberJpaRepository.findByUserNameAndAgeGreaterThan("AAA", 19);


         //then
         assertThat(list.get(0).getUserName()).isEqualTo("AAA");
         assertThat(list.get(0).getAge()).isEqualTo(20);
      }
}