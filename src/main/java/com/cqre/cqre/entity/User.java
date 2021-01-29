package com.cqre.cqre.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String studentId;

    private String loginId;

    private String password;

    private String email;

    @Embedded
    private Address address;

    @Builder
    public User(String name, String studentId, String loginId, String password, String email){
        Address address = new Address("Not yet Entered", "Not yet Entered");

        this.name = name;
        this.studentId = studentId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.address = address;
    }
}





