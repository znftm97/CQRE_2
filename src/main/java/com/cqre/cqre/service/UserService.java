package com.cqre.cqre.service;

import com.cqre.cqre.dto.user.*;
import com.cqre.cqre.entity.Address;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.user.CFindIdUserNotFoundException;
import com.cqre.cqre.exception.customexception.user.CFindPwUserNotFoundException;
import com.cqre.cqre.exception.customexception.user.CUserNotFoundException;
import com.cqre.cqre.exception.customexception.user.CValidationEmailException;
import com.cqre.cqre.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    /*회원가입*/
    @Transactional
    public void signUp(SignUpDto signUpDto) throws UnsupportedEncodingException, MessagingException {
        User user = User.builder()
                .name(signUpDto.getName())
                .studentId(signUpDto.getStudentId())
                .loginId(signUpDto.getLoginId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .emailVerified("false")
                .emailCheckToken(UUID.randomUUID().toString())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

        emailSend(user);
    }

    /*메일 전송*/
    public void emailSend(User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        /*ec2 환경에서 사용하기위한 코드 수정 부분*/
        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://15.165.245.31:8080/user/validationEmail?email=" +
                user.getEmail() +
                "&emailCheckToken=" +
                user.getEmailCheckToken() +
                "' target='_blank'><button>이메일 인증 하기</button></a>";

        messageHelper.setSubject("CQRE 회원 가입 인증");
        messageHelper.setText(html, true);
        messageHelper.setTo(user.getEmail());

        javaMailSender.send(message);
    }

    /*이메일 재전송*/
    public void emailSendRe(ValidationEmailReDto dto) throws MessagingException, UnsupportedEncodingException {
        User findUser = userRepository.findByLoginId(dto.getLoginId());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        /*ec2 환경에서 사용하기위한 코드 수정 부분*/
        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://15.165.245.31:8080/user/validationEmail?email=" +
                findUser.getEmail() +
                "&emailCheckToken=" +
                findUser.getEmailCheckToken() +
                "' target='_blank'><button>이메일 인증 하기</button></a>";

        messageHelper.setSubject("CQRE 회원 가입 인증");
        messageHelper.setText(html, true);
        messageHelper.setTo(findUser.getEmail());

        javaMailSender.send(message);
    }

    /*이메일 토큰 값 검증*/
    @Transactional
    public void validationEmailToken(String email, String emailCheckToken){
        User findUser = userRepository.findByEmail(email);

        if (!emailCheckToken.equals(findUser.getEmailCheckToken())) {
            throw new CValidationEmailException();
        }

        findUser.setEmailVerified("true");
    }

    /*id 찾기*/
    public String findId(UserDto userDto){
        User findUserByEmail = userRepository.OpFindByEmail(userDto.getEmail()).orElseThrow(CFindIdUserNotFoundException::new);
        User findUserByName = userRepository.findByName(userDto.getName()).orElseThrow(CFindIdUserNotFoundException::new);

        if (!findUserByEmail.getEmail().equals(findUserByName.getEmail())) {
            log.error("엔티티 조회 에러");
            throw new CFindIdUserNotFoundException();
        }

        return findUserByName.getLoginId();
    }

    /*비밀번호 변경 본인확인 메일 전송*/
    public void emailSendPw(UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        User findUserByEmail = userRepository.OpFindByEmail(userDto.getEmail()).orElseThrow(CFindPwUserNotFoundException::new);
        User findUserByLoginId = userRepository.CFindByLoginId(userDto.getLoginId()).orElseThrow(CFindPwUserNotFoundException::new);
        if (!findUserByEmail.getEmail().equals(findUserByLoginId.getEmail())) {
            throw new CFindPwUserNotFoundException();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        /*ec2 환경에서 사용하기위한 코드 수정 부분*/
        String html = "<h1>비밀번호 변경 본인확인</h1><br>" +
                "아래 버튼을 클릭하시면 본인 인증이 완료됩니다.<br>" +
                "<a href='http://15.165.245.31:8080/user/updatePassword?email=" +
                findUserByEmail.getEmail() +
                "' target='_blank'><button>인증 하기</button></a>";

        messageHelper.setSubject("CQRE 본인 확인");
        messageHelper.setText(html, true);
        messageHelper.setTo(findUserByEmail.getEmail());

        javaMailSender.send(message);
    }

    /*pw 변경*/
    @Transactional
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        User findUser = userRepository.OpFindByEmail(updatePasswordDto.getEmail()).orElseThrow(CFindPwUserNotFoundException::new);
        findUser.setPassword(passwordEncoder.encode(updatePasswordDto.getUpdatePassword()));
    }

    /*로그인 사용자 가져오기*/
    public User getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loginUser = User.builder().build();

        /*일반 로그인 사용자*/
        if (principal.getClass().getName().equals(loginUser.getClass().getName())) {
            loginUser = (User) principal;
            return userRepository.findByEmail(loginUser.getEmail());

        /*OAuth2 로그인 사용자*/
        } else {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
            Map<String, Object> attributes = defaultOAuth2User.getAttributes();

            /*카카오는 이중 Map 구조라 다르게 구현*/
            if (Objects.isNull(attributes.get("email"))) {
                Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
                return userRepository.findByEmail(kakao_account.get("email").toString());
            } else {
                return userRepository.findByEmail(attributes.get("email").toString());
            }
        }
    }

    /*회원 정보 수정*/
    @Transactional
    public void updateUserInfo(UserAddressDto userAddressDto){
        Address address = new Address(userAddressDto.getStreet(), userAddressDto.getDetail());

        User loginUser = getLoginUser();
        loginUser.setName(userAddressDto.getName());
        loginUser.setStudentId(userAddressDto.getStudentId());
        loginUser.setLoginId(userAddressDto.getLoginId());
        loginUser.setAddress(address);
    }

    /*회원 탈퇴*/
    /*post, galleryFile, UserCoupon, Comment, PostFile, Recommendation 등등... 다 삭제해줘야함*/
    /*@Transactional
    public void removeUser(){
        User loginUser = getLoginUser();
        userRepository.delete(loginUser);
    }*/
}
