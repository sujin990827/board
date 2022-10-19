package com.study.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded //내장타입을 포함했다라는 것을 표현
    private Address address;

    @OneToMany(mappedBy = "member")  //오더 테이블에 있는 멤버 필드에 의해서 맵핑이 된 것이다
    @JoinColumn(name = "member_id")
    private List<Order> orders = new ArrayList<>();

}
