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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*회원가입*/
    @Transactional
    public void signUp(SignUpDto signUpDto) throws UnsupportedEncodingException, MessagingException {
        User user = User.builder()
                .name(signUpDto.getName())
                .studentId(signUpDto.getStudentId())
                .loginId(signUpDto.getLoginId())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .email(signUpDto.getEmail())
                .build();

        userRepository.save(user);

        emailSend(user);
    }

    /*메일 전송*/
    public void emailSend(User user) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://localhost:8080/user/validationEmail?email=" +
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

        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://localhost:8080/user/validationEmail?email=" +
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
        User findUser = userRepository.OpFindByEmail(userDto.getEmail()).orElseThrow(CFindIdUserNotFoundException::new);

        if (!findUser.getName().equals(userDto.getName())) {
            logger.error("엔티티 조회 에러");
            throw new CFindIdUserNotFoundException();
        }

        return findUser.getLoginId();
    }

    /*비밀번호 변경 본인확인 메일 전송*/
    public void emailSendPw(UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        User findUser = userRepository.OpFindByEmail(userDto.getEmail()).orElseThrow(CFindPwUserNotFoundException::new);
        if (!userDto.getLoginId().equals(findUser.getLoginId())) {
            throw new CFindPwUserNotFoundException();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        String html = "<h1>비밀번호 변경 본인확인</h1><br>" +
                "아래 버튼을 클릭하시면 본인 인증이 완료됩니다.<br>" +
                "<a href='http://localhost:8080/user/updatePassword?email=" +
                findUser.getEmail() +
                "' target='_blank'><button>인증 하기</button></a>";

        messageHelper.setSubject("CQRE 본인 확인");
        messageHelper.setText(html, true);
        messageHelper.setTo(findUser.getEmail());

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
        User loginUser;

        try {
             loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            throw e;
        }

        return userRepository.CFindById(loginUser.getId()).orElseThrow(CUserNotFoundException::new);
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
