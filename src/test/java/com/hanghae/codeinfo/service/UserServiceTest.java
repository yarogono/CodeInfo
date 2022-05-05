package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.model.Board;
import com.hanghae.codeinfo.model.User;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.repository.UserRepository;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;


    @BeforeEach
    void before() {
        User user = User.builder()
                        .nickname("Q1w2")
                        .password("qwer")
                        .build();

        userRepository.save(user);
    }


    @DisplayName("회원 가입 아이디 중복검사 / 성공")
    @Test
    void 아이디_중복검사_성공() {
        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1w5",
//                "qwer",
//                "qwer"
//        );

        String nickname = "Q1w3";

        // when
        Boolean exception = userService.userDuplicateCheck(nickname);

        // then
        assertEquals("회원가입이 완료되었습니다.", exception);
    }


//    @DisplayName("회원 가입 아이디 중복검사 / 실패")
//    @Test
//    void 아이디_중복검사_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1w2",
//                "qwer",
//                "qwer"
//        );
//
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//
//        when(userRepository.findByNickname(requestDto.getNickname())).thenReturn(Optional.of(user));
//        // when
//        UserService userService = new UserService(passwordEncoder, userRepository);
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals(ExceptionMessages.NICKNAME_DUPLICATE, exception);
//    }
//
//    @DisplayName("비밀번호 확인 == 비밀번호 / 성공")
//    @Test
//    void 비밀번호_확인_성공() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1w2",
//                "qwer",
//                "qwer"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals("회원가입이 완료되었습니다.", exception);
//    }
//
//
//    @DisplayName("비밀번호 확인 == 비밀번호 / 실패")
//    @Test
//    void 비밀번호_확인_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1w2",
//                "qwerrrr",
//                "qwer"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals(ExceptionMessages.PWD_ARE_NOT_SAME, exception);
//    }
//
//
//    @DisplayName("비밀번호 최소 4자 이상 / 실패")
//    @Test
//    void 비밀번호_글자수_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1w2",
//                "qwe",
//                "qwe"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals("비밀번호는 4자이상의 비밀번호여야 합니다.", exception);
//    }
//
//
//    @DisplayName("비밀번호 닉네임과 같은 값 / 실패")
//    @Test
//    void 비밀번호_닉네임_같은지검사_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "qwer",
//                "qwer",
//                "qwer"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals("비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.", exception);
//    }
//
//
//    @DisplayName("닉네임 알파벳 대소문자, 숫자 / 실패")
//    @Test
//    void 닉네임_알파벳_대소문자_숫자_검사_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "q1w2e3",
//                "qwer",
//                "qwer"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.", exception);
//    }
//
//
//    @DisplayName("닉네임 3글자 이상 / 실패")
//    @Test
//    void 닉네임_3글자_이상_실패() {
//        // given
//        UserJoinRequestDto requestDto = new UserJoinRequestDto(
//                "Q1",
//                "qwer",
//                "qwer"
//        );
//        User user = new User(
//                requestDto.getNickname(),
//                requestDto.getPassword()
//        );
//        UserService userService = new UserService(passwordEncoder, userRepository);
//
//        // when
//        String exception = userService.saveUser(requestDto);
//
//        // then
//        assertEquals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.", exception);
//    }
}