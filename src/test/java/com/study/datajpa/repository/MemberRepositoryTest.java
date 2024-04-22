package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import com.study.datajpa.entity.Team;
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

    @Autowired
    TeamRepository teamRepository;

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
        List<Member> findUser = memberRepository.findUser("AAA");

        //then
     }

     @Test
     public void usernameListTest() throws Exception {
         //given
         Member member1 = Member.builder()
                 .username("AAA")
                 .age(15)
                 .build();
         Member member2 = Member.builder()
                 .username("BBB")
                 .age(20)
                 .build();
         memberRepository.save(member1);
         memberRepository.save(member2);
         //when
         List<String> usernameList = memberRepository.findUsernameList();
         assertThat(usernameList.size()).isEqualTo(2);
         assertThat(usernameList.get(0)).isEqualTo("AAA");
         assertThat(usernameList.get(1)).isEqualTo("BBB");

         //then
      }

      @Test
      public void findMemberDto_test() throws Exception {
          //given
          Team team = Team.builder()
                  .name("teamA")
                  .build();
          teamRepository.save(team);
          Member member1 = Member.builder()
                  .username("AAA")
                  .age(15)
                  .build();
          member1.changeTeam(team);
          memberRepository.save(member1);

          List<MemberDto> memberDtoList = memberRepository.findMemberDto();
          assertThat(memberDtoList.get(0).getTeamName()).isEqualTo("teamA");

          //when

          //then
       }


       @Test
       public void findByNames_test() throws Exception {
           //given
           Member member1 = Member.builder()
                   .username("AAA")
                   .age(15)
                   .build();
           Member member2 = Member.builder()
                   .username("BBB")
                   .age(20)
                   .build();
           memberRepository.save(member1);
           memberRepository.save(member2);
           //when
           List<Member> findMembers = memberRepository.findByNames(List.of("AAA", "BBB"));

           assertThat(findMembers.size()).isEqualTo(2);
           assertThat(findMembers.get(0).getUsername()).isEqualTo("AAA");
           assertThat(findMembers.get(1).getUsername()).isEqualTo("BBB");

           //then
        }
}
