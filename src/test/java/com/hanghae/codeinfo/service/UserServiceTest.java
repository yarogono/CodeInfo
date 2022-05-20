package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.model.User;
import com.hanghae.codeinfo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    private String userIdFirst = "Q1w2";
    private String userIdSecond = "Q1w3";

    @BeforeEach
    void before() {
        User user = User.builder()
                        .nickname(userIdFirst)
                        .password("qwer")
                        .build();

        userRepository.save(user);
    }


    @Test
    @DisplayName("회원가입 아이디 중복검사 / 실패")
    void 아이디_중복검사_실패() {
        // given, when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.userDuplicateCheck(userIdFirst);
        });

        // then
        assertEquals(ExceptionMessages.NICKNAME_DUPLICATE, exception.getMessage());
    }


    @DisplayName("회원가입 아이디 중복검사 / 성공")
    @Test
    void 아이디_중복검사_성공() {
        // given, when
        Boolean result = userService.userDuplicateCheck(userIdSecond);

        // then
        assertEquals(false, result);
    }

    @DisplayName("회원가입 / 성공")
    @Test
    void 회원가입_성공() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w6",
                "qwer",
                "qwer"
        );

        // when
        String result = userService.saveUser(requestDto);

        // then
        assertEquals("회원가입이 완료되었습니다.", result);
    }


    @DisplayName("비밀번호 확인 == 비밀번호 / 실패")
    @Test
    void 비밀번호_확인_실패() {
        // given
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwerrrr",
                "qwer"
        );

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userJoinRequestDto);
        });

        // then
        assertEquals(ExceptionMessages.PWD_ARE_NOT_SAME, exception.getMessage());
    }


    @DisplayName("비밀번호 최소 4자 이상 / 실패")
    @Test
    void 비밀번호_글자수_실패() {
        // given
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(
                "Q1w7",
                "qwe",
                "qwe"
        );

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userJoinRequestDto);
        });

        // then
        assertEquals("비밀번호는 4자이상의 비밀번호여야 합니다.", exception.getMessage());
    }


    @DisplayName("비밀번호 닉네임과 같은 값 / 실패")
    @Test
    void 비밀번호_닉네임_같은지검사_실패() {
        // given
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(
                "qwer",
                "qwer",
                "qwer"
        );

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userJoinRequestDto);
        });

        // then
        assertEquals(ExceptionMessages.NICKNAME_AND_PWD_SAME, exception.getMessage());
    }


    @DisplayName("닉네임 알파벳 대소문자, 숫자 / 실패")
    @Test
    void 닉네임_알파벳_대소문자_숫자_검사_실패() {
        // given
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(
                "q1w2e3",
                "qwer",
                "qwer"
        );


        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userJoinRequestDto);
        });

        // then
        assertEquals(ExceptionMessages.ILLEGAL_NICKNAME_PWD, exception.getMessage());
    }


    @DisplayName("닉네임 3글자 이상 / 실패")
    @Test
    void 닉네임_3글자_이상_실패() {
        // given
        UserJoinRequestDto userJoinRequestDto = new UserJoinRequestDto(
                "Q1",
                "qwer",
                "qwer"
        );

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(userJoinRequestDto);
        });

        // then
        assertEquals(ExceptionMessages.ILLEGAL_NICKNAME_PWD, exception.getMessage());
    }
}