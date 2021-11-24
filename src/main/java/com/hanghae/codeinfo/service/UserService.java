package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.UserRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public void userJoin(UserRequestDto requestDto) throws NoSuchAlgorithmException {
        if(requestDto.getPassword2() == requestDto.getPassword())
            return;

        // 암호화 필요

//        User user = User.builder()
//                .nickname(form.getNickname())
//                .password()
//                .build();
//        userRepository.save(user);
    }

    @Transactional
    public boolean userDup() {
//        userRepository.findById();
        return true;
    }


}
