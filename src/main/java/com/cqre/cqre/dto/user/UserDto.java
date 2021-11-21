package com.cqre.cqre.dto.user;

import com.cqre.cqre.domain.user.Address;
import com.cqre.cqre.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private String loginId;
    private String studentId;
    private Address address;
    private String emailVerified;
    private String emailCheckToken;
    private String password;

    public UserDto(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.loginId = user.getLoginId();
        this.studentId = user.getStudentId();
        this.address = user.getAddress();
        this.emailVerified = user.getEmailVerified();
        this.emailCheckToken = user.getEmailCheckToken();
        this.password = user.getPassword();
    }

}
