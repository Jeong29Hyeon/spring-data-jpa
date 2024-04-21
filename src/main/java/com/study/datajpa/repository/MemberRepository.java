package com.study.datajpa.repository;

import com.study.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String userName, int age);

    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username")
    List<Member> findUser(@Param("username") String username);
}
