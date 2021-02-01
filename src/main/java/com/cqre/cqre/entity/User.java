package com.cqre.cqre.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    private String emailVerified;

    private String emailCheckToken;

    private String role = "ROLE_USER";

    @Builder
    public User(String name, String studentId, String loginId, String password, String email){
        Address address = new Address("Not yet Entered", "Not yet Entered");

        this.name = name;
        this.studentId = studentId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.address = address;
        this.emailVerified = "false";
        this.emailCheckToken = UUID.randomUUID().toString();
    }
}






