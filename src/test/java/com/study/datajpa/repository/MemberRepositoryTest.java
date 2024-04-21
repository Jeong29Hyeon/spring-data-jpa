package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        //given
        Member member = Member.builder()
                .username("memberA")
                .build();
        Member savedMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
     }

    @Test
    public void basicCRUD() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("member1")
                .build();
        Member member2 = Member.builder()
                .username("member2")
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);

        //단건 조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();

        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회 검증
        System.out.println("-----------------------------------------------------");
        List<Member> all = memberRepository.findAll();
        System.out.println("-----------------------------------------------------");
        assertThat(all.size()).isEqualTo(2);

        //카운트 검증
        System.out.println("-----------------------------------------------------");
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제 검증
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long deletedCount = memberRepository.count();
        System.out.println("-----------------------------------------------------");
        assertThat(deletedCount).isEqualTo(0);
    }

    @Test
    public void testFindByUserNameAndAgeGreaterThan() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("AAA")
                .age(15)
                .build();
        Member member2 = Member.builder()
                .username("AAA")
                .age(20)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> list = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 19);


        //then
        assertThat(list.get(0).getUsername()).isEqualTo("AAA");
        assertThat(list.get(0).getAge()).isEqualTo(20);
    }

    @Test
    public void testQuery() throws Exception {
        //given
        Member member1 = Member.builder()
                .username("AAA")
                .age(15)
                .build();
        Member member2 = Member.builder()
                .username("AAA")
                .age(20)
                .build();
        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        memberRepository.findUser("AAA");

        //then
     }
}
