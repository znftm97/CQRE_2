package com.cqre.cqre.dto;

import lombok.Data;

@Data
public class UserAddressDto {
    private String name;
    private String email;
    private String loginId;
    private String studentId;
    private String street;
    private String detail;
    private String emailVerified;
    private String emailCheckToken;
    private String password;
}
