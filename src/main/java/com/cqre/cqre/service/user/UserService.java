package com.cqre.cqre.service.user;

import com.cqre.cqre.dto.SignUpDto;
import com.cqre.cqre.entity.User;
import com.cqre.cqre.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
    }
}
