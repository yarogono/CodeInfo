package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.model.User;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String saveUser(UserJoinRequestDto requestDto) {

        String userId = requestDto.getUserId();

        String userPassword = requestDto.getPassword();
        String userPassword2 = requestDto.getPassword2();

        // 유저아이디 중복 체크
        findUserByUserId(userId);

        // 회원가입 유효성 검사
        userValidationCheck(userId, userPassword, userPassword2);

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        User user = User.builder()
                .nickname(userId)
                .password(password)
                .build();
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }


    public boolean userDuplicateCheck(String userId) {

        findUserByUserId(userId);

        return false;
    }

    private void findUserByUserId(String userId) {
        Optional<User> found = userRepository.findByUserId(userId);
        if(found.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessages.NICKNAME_DUPLICATE);
        }
    }

    private void userValidationCheck(String userId, String userPassword, String userPassword2) {
        // 닉네임에 같은 값이 포함되어있으면 에러발생
        if(userPassword.contains(userId)) {
            throw new IllegalArgumentException(ExceptionMessages.NICKNAME_AND_PWD_SAME);
        }
        // 입력된 비밀 번호 값이 같지 않으면 회원가입 불가
        if(!(userPassword.equals(userPassword2))) {
            throw new IllegalArgumentException(ExceptionMessages.PWD_ARE_NOT_SAME);
        }

        String patternId = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{3,}$";
        String patterNpw = ".{4,}";

        boolean regexId = Pattern.matches(patternId, userId);
        // 아아디 조건 일치여부
        if(!regexId) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_NICKNAME_PWD);
        }

        boolean regexPw = Pattern.matches(patterNpw, userPassword);
        // 비밀번호 조건 일치여부
        if(!regexPw) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_PWD_LENGTH);
        }
    }
}
