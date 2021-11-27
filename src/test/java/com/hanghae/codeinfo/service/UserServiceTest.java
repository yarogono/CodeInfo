package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.User;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;


    @DisplayName("회원 가입 아이디 중복검사 / 성공")
    @Test
    void test1() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwer",
                "qwer"
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);


        // then
        assertEquals("회원가입이 완료되었습니다.", exception);
    }


    @DisplayName("회원 가입 아이디 중복검사 / 실패")
    @Test
    void test2() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwer",
                "qwer"
        );

        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );

        when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(Optional.of(user));
        // when
        UserService userService = new UserService(passwordEncoder, userRepository);
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("중복된 닉네임입니다.", exception);
    }

    @DisplayName("비밀번호 확인 == 비밀번호 / 성공")
    @Test
    void test3() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwer",
                "qwer"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("회원가입이 완료되었습니다.", exception);
    }


    @DisplayName("비밀번호 확인 == 비밀번호 / 실패")
    @Test
    void test4() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwerrrr",
                "qwer"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("비밀번호가 같지 않습니다.", exception);
    }


    @DisplayName("비밀번호 최소 4자 이상 / 실패")
    @Test
    void test5() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1w2",
                "qwe",
                "qwe"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("비밀번호는 4자이상의 비밀번호여야 합니다.", exception);
    }


    @DisplayName("비밀번호 닉네임과 같은 값 / 실패")
    @Test
    void test6() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "qwer",
                "qwer",
                "qwer"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.", exception);
    }


    @DisplayName("닉네임 알파벳 대소문자, 숫자 / 실패")
    @Test
    void test7() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "q1w2e3",
                "qwer",
                "qwer"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.", exception);
    }


    @DisplayName("닉네임 3글자 이상 / 실패")
    @Test
    void test9() {
        // given
        UserJoinRequestDto requestDto = new UserJoinRequestDto(
                "Q1",
                "qwer",
                "qwer"
        );
        User user = new User(
                requestDto.getNickname(),
                requestDto.getPassword()
        );
        UserService userService = new UserService(passwordEncoder, userRepository);

        // when
        String exception = userService.userJoin(requestDto);

        // then
        assertEquals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.", exception);
    }
}