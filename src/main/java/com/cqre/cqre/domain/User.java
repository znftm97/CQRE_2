package com.cqre.cqre.domain;

import com.cqre.cqre.dto.user.UserAddressDto;
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

    @Column(unique = true, nullable = false)
    private String email;

    @Embedded
    private Address address;

    private String emailVerified;

    private String emailCheckToken;

    private String role = "ROLE_USER";

    @Builder
    public User(String name, String studentId, String loginId, String password, String email, String emailVerified, String emailCheckToken, String role){
        Address address = new Address("Not yet Entered", "Not yet Entered");

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

    // == 비즈니스 로직 == //

    /*소셜 계정 정보 업데이트 시 DB값도 업데이트 하기 위함*/
    public User update(String name) {
        this.name = name;

        return this;
    }

    /*비밀번호 변경*/
    public void updatePassword(String password) {
        this.password = password;
    }

    /*이메일 인증 성공*/
    public void updateEmailVerified() {
        this.emailVerified = "true";
    }

    /*회원 정보 수정*/
    public void updateUserInfo(UserAddressDto dto) {
        this.address = new Address(dto.getStreet(), dto.getDetail());
        this.name = dto.getName();
        this.studentId = dto.getStudentId();
        this.loginId = dto.getLoginId();
    }
}






