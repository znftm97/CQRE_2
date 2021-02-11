package com.cqre.cqre.dto.user;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpDto {
    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String name;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Size(min = 8, max = 8, message = "학번은 8자리여야 합니다.")
    private String studentId;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    private String loginId;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문,숫자,특수문자가 포함된 8~20 자리의 비밀번호여야 합니다.")
    private String password;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
}
