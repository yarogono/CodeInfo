package com.hanghae.codeinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.KakaoUserService;
import com.hanghae.codeinfo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @GetMapping("/user/login")
    public String userLoginPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(userDetails != null ) {
            throw new IllegalArgumentException(ExceptionMessages.ALLREADY_LOGIN);
        }

        return "login";
    }



    @GetMapping("/user/join")
    public String userJoinPage(
            @ModelAttribute("form") UserJoinRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(userDetails != null) {
            throw new IllegalArgumentException(ExceptionMessages.ALLREADY_LOGIN);
        }

        return "join";
    }

    @PostMapping("/user/join")
    public String saveUser(
            @ModelAttribute("form")
            UserJoinRequestDto requestDto
    ) {
        Boolean isDuplicate = userService.userDuplicateCheck(requestDto.getUserId());
        if(!isDuplicate) {
            return "join";
        }

        userService.saveUser(requestDto);
        return "redirect:/user/login";
    }


    @PostMapping("/user/duplicate")
    @ResponseBody
    public boolean userDuplicate(
            @RequestBody String nickname
    ) {
        return userService.userDuplicateCheck(nickname);
    }


    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(
            @RequestParam String code
    ) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }

}
