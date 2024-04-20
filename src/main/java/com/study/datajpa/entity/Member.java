package com.study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of= {"id", "userName", "age"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public Member(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }



    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
