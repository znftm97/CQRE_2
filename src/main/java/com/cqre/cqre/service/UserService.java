package com.cqre.cqre.service;

import com.cqre.cqre.domain.user.User;
import com.cqre.cqre.dto.user.*;
import com.cqre.cqre.exception.customexception.user.*;
import com.cqre.cqre.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
        User user = signUpDto.toEntity();
        userRepository.save(user);

        sendEmail(user);
    }

    /*메일 전송*/
    public void sendEmail(User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        /*ec2 환경에서 사용하기위한 코드 수정 부분*/
        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://ec2-54-180-142-70.ap-northeast-2.compute.amazonaws.com:8080/users/validation-email?email=" +
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
        User findUser = userRepository.findByLoginId(dto.getLoginId()).orElseThrow(CUserNotFoundExceptionToEmailPage::new);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        /*ec2 환경에서 사용하기위한 코드 수정 부분*/
        String html = "<h1>이메일 인증</h1><br>" +
                "아래 버튼을 클릭하시면 이메일 인증이 완료됩니다.<br>" +
                "<a href='http://ec2-54-180-142-70.ap-northeast-2.compute.amazonaws.com:8080/users/validation-email?email=" +
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
        User findUser = userRepository.findByEmail(email).orElseThrow(CUserNotFoundExceptionToPwPage::new);

        if (!emailCheckToken.equals(findUser.getEmailCheckToken())) {
            throw new CValidationEmailException();
        }

        findUser.updateEmailVerified();
    }

    /*id 찾기*/
    public String findId(UserDto userDto){
        User findUserByEmail = userRepository.findByEmail(userDto.getEmail()).orElseThrow(CUserNotFoundExceptionToIdPage::new);
        User findUserByName = userRepository.findByName(userDto.getName()).orElseThrow(CUserNotFoundExceptionToIdPage::new);

        if (!findUserByEmail.getEmail().equals(findUserByName.getEmail())) {
            log.error("엔티티 조회 에러");
            throw new CUserNotFoundExceptionToPwPage();
        }

        return findUserByName.getLoginId();
    }

    /*비밀번호 변경 본인확인 메일 전송*/
    public void emailSendPw(UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        User findUserByEmail = userRepository.findByEmail(userDto.getEmail()).orElseThrow(CUserNotFoundExceptionToPwPage::new);
        User findUserByLoginId = userRepository.findByLoginId(userDto.getLoginId()).orElseThrow(CUserNotFoundExceptionToPwPage::new);
        if (!findUserByEmail.getEmail().equals(findUserByLoginId.getEmail())) {
            throw new CUserNotFoundExceptionToPwPage();
        }

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

        String html = "<h1>비밀번호 변경 본인확인</h1><br>" +
                "아래 버튼을 클릭하시면 본인 인증이 완료됩니다.<br>" +
                "<a href='http://ec2-54-180-142-70.ap-northeast-2.compute.amazonaws.com:8080/users/update-password?email=" +
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
        User findUser = userRepository.findByEmail(updatePasswordDto.getEmail()).orElseThrow(CUserNotFoundExceptionToPwPage::new);
        findUser.updatePassword(passwordEncoder.encode(updatePasswordDto.getUpdatePassword()));
    }

    /*로그인 사용자 조회*/
    public User getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        /*일반 로그인 사용자*/
        if (principal instanceof User) {
            User loginUser = (User) principal;
            return userRepository.findByEmail(loginUser.getEmail()).orElseThrow(CUserNotFoundException::new);

        /*OAuth2 로그인 사용자*/
        } else {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) principal;
            Map<String, Object> attributes = defaultOAuth2User.getAttributes();

            /*카카오는 이중 Map 구조라 다르게 구현*/
            if (attributes.get("email") == null) {
                Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
                return userRepository.findByEmail(kakaoAccount.get("email").toString()).orElseThrow(CUserNotFoundException::new);
            } else {
                return userRepository.findByEmail(attributes.get("email").toString()).orElseThrow(CUserNotFoundException::new);
            }
        }
    }

    /*회원 정보 수정*/
    @Transactional
    public void updateUserInfo(UserAddressDto userAddressDto){
        User loginUser = getLoginUser();
        loginUser.updateUserInfo(userAddressDto.getStreet(), userAddressDto.getDetail(), userAddressDto.getName(), userAddressDto.getStudentId(), userAddressDto.getLoginId());
    }

}
