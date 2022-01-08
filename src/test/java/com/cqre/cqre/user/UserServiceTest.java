package com.cqre.cqre.user;

import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.user.SignUpDto;
import com.cqre.cqre.exception.ExceptionStatus;
import com.cqre.cqre.exception.customexception.user.CUserEmailOverlapException;
import com.cqre.cqre.exception.customexception.user.CUserLoginIdOverlapException;
import com.cqre.cqre.repository.UserRepository;
import com.cqre.cqre.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("회원가입 실패 - 이메일이 중복되면 안된다.")
    public void existEmail(@Mock SignUpDto dto, @Mock User user){
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(CUserEmailOverlapException.class)
                .isThrownBy(() -> userService.signUp(dto))
                .withMessageMatching(ExceptionStatus.OVERLAP_EMAIL.getMessage());
    }

    @Test
    @DisplayName("회원가입 실패 - 로그인 아이디가 중복되면 안된다.")
    public void existLoginId(@Mock SignUpDto dto, @Mock User user){
        when(userRepository.findByLoginId(dto.getLoginId())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(CUserLoginIdOverlapException.class)
                .isThrownBy(() -> userService.signUp(dto))
                .withMessageMatching(ExceptionStatus.OVERLAP_LOGIN_ID.getMessage());
    }

    @Test
    @DisplayName("유저가 회원가입을 한다.")
    @Disabled("이메일 전송 코드 테스트는 나중에..")
    public void signUp() throws MessagingException, UnsupportedEncodingException {

        //given
        SignUpDto dto = SignUpDto.builder()
                .name("name")
                .studentId("studentId")
                .loginId("loginId")
                .email("email@email.com")
                .password("password")
                .build();

        User user = dto.toEntity(passwordEncoder);
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByLoginId(dto.getLoginId())).thenReturn(Optional.empty());

        //when
        userService.signUp(dto);

        //then
        assertThat(userRepository.findByName(dto.getName())).isEqualTo(Optional.of(user));
        assertThat(userRepository.findByLoginId(dto.getLoginId())).isEqualTo(Optional.of(user));
        assertThat(userRepository.findByEmail(dto.getEmail())).isEqualTo(Optional.of(user));
    }

}
