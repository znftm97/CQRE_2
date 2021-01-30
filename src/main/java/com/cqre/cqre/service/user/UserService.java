package com.cqre.cqre.service.user;

import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CValidationEmailException;
import com.cqre.cqre.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    /*토큰 값 검증*/
    @Transactional
    public void validationEmailToken(String email, String emailCheckToken){
        User findUser = userRepository.findByEmail(email);

        if (!emailCheckToken.equals(findUser.getEmailCheckToken())) {
            throw new CValidationEmailException();
        }

        findUser.setEmailVerified("true");
    }
}
