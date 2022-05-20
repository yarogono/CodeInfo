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


        //닉네임에 같은 값이 포함되어있으면 에러내기, indexof가 -1 이면 안에 포함이 안돼있는것
        if(requestDto.getPassword().indexOf(requestDto.getUserId())!=-1) {
            throw new IllegalArgumentException(ExceptionMessages.NICKNAME_AND_PWD_SAME);
        }
        // 입력된 비밀 번호 값이 같지 않으면 회원가입 불가
        if(!(requestDto.getPassword().equals(requestDto.getPassword2()))) {
            throw new IllegalArgumentException(ExceptionMessages.PWD_ARE_NOT_SAME);
        }

        // 정규표현식 일치 여부에 따른 에러
        Optional<User> found = userRepository.findByUserId(requestDto.getUserId());
        if(found.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessages.NICKNAME_DUPLICATE);
        }


        String patternId = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{3,}$";
        String ids = requestDto.getUserId();
        String patterNpw = ".{4,}";
        String pws = requestDto.getPassword();
        boolean regexId = Pattern.matches(patternId, ids);
        // 아아디 조건 일치여부
        if(!regexId) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_NICKNAME_PWD);
        }

        boolean regexPw = Pattern.matches(patterNpw, pws);
        // 비밀번호 조건 일치여부
        if(!regexPw) {
            throw new IllegalArgumentException(ExceptionMessages.ILLEGAL_PWD_LENGTH);
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        User user = User.builder()
                .nickname(requestDto.getUserId())
                .password(password)
                .build();
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }


    public boolean userDuplicateCheck(String userId) {

        Optional<User> findUser = userRepository.findByUserId(userId);

        if(findUser.isPresent()) {
            throw new IllegalArgumentException(ExceptionMessages.NICKNAME_DUPLICATE);
        }

        return false;
    }
}
