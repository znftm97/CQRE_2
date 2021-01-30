package com.cqre.cqre.service.user;

import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.exception.customexception.CValidationEmailException;
import com.cqre.cqre.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    /*회원가입*/
    @Transactional
    public void signUp(SignUpDto signUpDto){

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
    public void emailSend(User user){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("CQRE 회원 가입 인증");
        mailMessage.setText(
                "아래 링크를 클릭하시면 이메일 인증이 완료됩니다." +
                "<a href='http://localhost:8080/user/validationEmail?email=" +
                user.getEmail() +
                "&emailCheckToken=" +
                user.getEmailCheckToken() +
                "' target='_blank'>이메일 인증 확인</a>");

        /*mailMessage.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
                .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
                .append("<a href='http://localhost:8080/user/validationEmail?email=")
                .append(user.getEmail())
                .append("&emailCheckToken=")
                .append(user.getEmailCheckToken())
                .append("' target='_blank'>이메일 인증 확인</a>")
                .toString());*/

        javaMailSender.send(mailMessage);
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
