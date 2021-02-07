package com.cqre.cqre.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UpdatePasswordDto extends UserDto {

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문,숫자,특수문자가 포함된 8~20 자리의 비밀번호여야 합니다.")
    private String updatePassword;

    @NotEmpty(message = "필수로 입력해야 합니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "영문,숫자,특수문자가 포함된 8~20 자리의 비밀번호여야 합니다.")
    private String updatePasswordRe;
}
