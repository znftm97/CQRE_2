package com.cqre.cqre.dto.user;

import com.cqre.cqre.entity.Address;
import lombok.Data;

@Data
public class UserDto {

    private String name;
    private String email;
    private String loginId;
    private String studentId;
    private Address address;
    private String emailVerified;
    private String emailCheckToken;
    private String password;
}
