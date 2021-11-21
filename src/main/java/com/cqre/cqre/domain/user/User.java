package com.cqre.cqre.domain.user;

import com.cqre.cqre.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String studentId;

    @Column(unique = true, nullable = false)
    private String loginId;

    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Embedded
    private Address address;

    private String emailVerified;

    private String emailCheckToken;

    private String role = "ROLE_USER";

    @Builder
    public User(String name, String studentId, String loginId, String password, String email, Address address, String emailVerified, String emailCheckToken, String role){
        this.name = name;
        this.studentId = studentId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.address = address;
        this.emailVerified = emailVerified;
        this.emailCheckToken = emailCheckToken;
        this.role = role;
    }

    public User updateName(String name) {
        this.name = name;
        return this;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEmailVerified() {
        this.emailVerified = "true";
    }

    public void updateUserInfo(String street, String detail, String name, String studentId, String loginId){
        Address address = new Address(street, detail);
        this.address = address;
        this.name = name;
        this.studentId = studentId;
        this.loginId = loginId;
    }

}






