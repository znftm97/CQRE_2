package com.cqre.cqre.user;

import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.user.SignUpDto;
import com.cqre.cqre.dto.user.UpdatePasswordDto;
import com.cqre.cqre.dto.user.UserDto;
import com.cqre.cqre.exception.ExceptionStatus;
import com.cqre.cqre.exception.customexception.user.*;
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
import java.util.UUID;

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
    @DisplayName("[회원가입 실패] - 이메일이 중복되면 안된다.")
    public void existEmail(@Mock SignUpDto dto, @Mock User user){
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(CUserEmailOverlapException.class)
                .isThrownBy(() -> userService.signUp(dto))
                .withMessageMatching(ExceptionStatus.OVERLAP_EMAIL.getMessage());
    }

    @Test
    @DisplayName("[회원가입 실패] - 로그인 아이디가 중복되면 안된다.")
    public void existLoginId(@Mock SignUpDto dto, @Mock User user){
        when(userRepository.findByLoginId(dto.getLoginId())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(CUserLoginIdOverlapException.class)
                .isThrownBy(() -> userService.signUp(dto))
                .withMessageMatching(ExceptionStatus.OVERLAP_LOGIN_ID.getMessage());
    }

    @Test
    @DisplayName("[회원가입 성공] - 유저가 회원가입을 한다.")
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

    @Test
    @DisplayName("[이메일 인증 실패] - 이메일 인증 토큰 값이 다르면 인증에 실패한다.")
    public void failureValidationEmailToken(){
        User user = User.builder()
                .email("email@email.com")
                .emailCheckToken(UUID.randomUUID().toString())
                .build();

        String emailCheckToken = UUID.randomUUID().toString();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThatExceptionOfType(CValidationEmailException.class)
                .isThrownBy(() -> userService.validationEmailToken(user.getEmail(), emailCheckToken))
                .withMessageMatching(ExceptionStatus.VALIDATION_EMAIL_EXCEPTION.getMessage());
    }

    @Test
    @DisplayName("[이메일 인증 성공] - 이메일 인증 토큰 값이 같으면 인증에 성공한다.")
    public void successValidationEmailToken(){
        User user = User.builder()
                .email("email@email.com")
                .emailCheckToken(UUID.randomUUID().toString())
                .build();

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        userService.validationEmailToken(user.getEmail(), user.getEmailCheckToken());
    }

    @Test
    @DisplayName("[loginId 찾기 실패] - 존재하지 않는 이메일이면 실패한다.")
    public void findIdFailByEmail(@Mock UserDto dto){
        assertThatExceptionOfType(CUserNotFoundExceptionToIdPage.class)
                .isThrownBy(() -> userService.findId(dto))
                .withMessageMatching(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_ID_PAGE.getMessage());
    }

    @Test
    @DisplayName("[loginId 찾기 실패] - 존재하지 않는 이름이면 실패한다.")
    public void findIdFailByName(@Mock UserDto dto){
        assertThatExceptionOfType(CUserNotFoundExceptionToIdPage.class)
                .isThrownBy(() -> userService.findId(dto))
                .withMessageMatching(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_ID_PAGE.getMessage());
    }

    @Test
    @DisplayName("[loginId 찾기 실패] - 이름으로 조회한 유저와 이메일로 조회한 유저가 다르면 실패한다.")
    public void findIdFailNotEquals(){
        //given
        User user1 = User.builder()
                .name("name1")
                .email("email1@email.com")
                .build();
        UserDto dto1 = new UserDto(user1);

        User user2 = User.builder()
                .name("name2")
                .email("email2@email.com")
                .build();

        when(userRepository.findByEmail(dto1.getEmail())).thenReturn(Optional.of(user1));
        when(userRepository.findByName(dto1.getName())).thenReturn(Optional.of(user2));

        //when && then
        assertThatExceptionOfType(CUserNotFoundExceptionToPwPage.class)
                .isThrownBy(() -> userService.findId(dto1))
                .withMessageMatching(ExceptionStatus.USER_NOT_FOUND_EXCEPTION_TO_PW_PAGE.getMessage());
    }

    @Test
    @DisplayName("[loginId 찾기 성공] - 이메일과 이름으로 loginId를 찾을 수 있다.")
    public void successFindId(){
        //given
        User user = User.builder()
                .name("name")
                .email("email@email.com")
                .build();
        UserDto dto = new UserDto(user);

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.findByName(dto.getName())).thenReturn(Optional.of(user));

        //when
        String loginId = userService.findId(dto);

        //then
        assertThat(loginId).isEqualTo(dto.getLoginId());
    }

    @Test
    @DisplayName("[비밀번호 변경 성공] - 비밀번호를 변경할 수 있다.")
    public void updatePassword(@Mock UpdatePasswordDto dto){
        //given
        User user = User.builder()
                .name("name")
                .email("email@email.com")
                .password("password")
                .build();
        dto.setUpdatePassword("updatePassword");
        dto.setUpdatePasswordRe("updatePassword");
        dto.setEmail(user.getEmail());

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        //when && then
        userService.updatePassword(dto);
    }

}
