package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.User;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;

import org.apache.tomcat.util.net.jsse.JSSEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public void userJoin(UserJoinRequestDto requestDto) {
        String nickname = requestDto.getNickname();

        // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());


        User user = new User(nickname, password);
        userRepository.save(user);
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
