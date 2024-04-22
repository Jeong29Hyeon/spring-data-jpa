package com.study.datajpa.repository;

import com.study.datajpa.dto.MemberDto;
import com.study.datajpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String userName, int age);

    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username")
    List<Member> findUser(@Param("username") String username);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new com.study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findListByUsername(String username); //리스트로
    Member findMemberByUsername(String username); //그냥 Member 단건
    Optional<Member> findOptionalByUsername(String username); //Optional 단건

    //Page 이용
    @Query(value = "select m from Member m left join fetch m.team t",
    countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //Slice 이용
    Slice<Member> findByAgeGreaterThan(int age, Pageable pageable);
}
