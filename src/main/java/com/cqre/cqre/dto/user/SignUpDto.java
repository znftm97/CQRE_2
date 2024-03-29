package com.cqre.cqre.dto.user;

import com.cqre.cqre.domain.user.Address;
import com.cqre.cqre.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public SignUpDto(String name, String studentId, String loginId, String password, String email) {
        this.name = name;
        this.studentId = studentId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

    public User toEntity(PasswordEncoder passwordEncoder){
        Address address = new Address("Not yet Entered", "Not yet Entered");

        return User.builder()
                .name(name)
                .studentId(studentId)
                .loginId(loginId)
                .password(passwordEncoder.encode(password))
                .email(email)
                .address(address)
                .emailVerified("true") // 구글 계정 2단계 보안 설정으로 gsmtp 사용 못함, 일단 회원가입하면 인증된 유저로 판단
                .emailCheckToken(UUID.randomUUID().toString())
                .role("ROLE_USER")
                .build();
    }

}
