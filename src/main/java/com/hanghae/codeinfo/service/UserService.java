package com.hanghae.codeinfo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanghae.codeinfo.domain.User;
import com.hanghae.codeinfo.dto.KakaoUserInfoDto;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public String userJoin(UserJoinRequestDto requestDto) {

        //닉네임에 같은 값이 포함되어있으면 에러내기, indexof가 -1 이면 안에 포함이 안돼있는것
        if(requestDto.getPassword().indexOf(requestDto.getNickname())!=-1) {
            return "비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.";
        }
        // 입력된 비밀 번호 값이 같지 않으면 회원가입 불가
        if(!(requestDto.getPassword().equals(requestDto.getPassword2()))) {
            return "비밀번호가 같지 않습니다.";
        }

        // 정규표현식 일치 여부에 따른 에러
        Optional<User> found = userRepository.findByNickname(requestDto.getNickname());
        if(found.isPresent()) {
            return "중복된 닉네임입니다.";
        }


        String patternid = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{3,}$";
        String ids = requestDto.getNickname();
        String patternpw = ".{4,}";
        String pws = requestDto.getPassword();
        boolean regexid = Pattern.matches(patternid, ids);
        // 아아디 조건 일치여부
        if(regexid==false) {
            return "닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.";
        }

        boolean regexpw = Pattern.matches(patternpw, pws);
        // 비밀번호 조건 일치여부
        if(regexpw==false) {
            return "비밀번호는 4자이상의 비밀번호여야 합니다.";
        }

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        User user = new User().builder()
                .nickname(requestDto.getNickname())
                .password(password)
                .build();
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }


    public boolean userDuplicateCheck(String nickname) {

        boolean isEmpty = false;
        User found = userRepository.findByNickname(nickname)
                .orElse(null);
        if(found == null) {
            isEmpty = true;
        }
        return isEmpty;

    }
}
