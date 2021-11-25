package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.dto.UserJoinRequestDto;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login")
    public String loginPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(userDetails != null) {
            return "redirect:/";
        }
        return "login";
    }



    @GetMapping("/user/join")
    public String joinPage(
            @ModelAttribute("form") UserJoinRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(userDetails != null) {
            return "redirect:/";
        }
        return "join";
    }

    @PostMapping("/user/join")
    public String userJoin(
            @ModelAttribute("form")
            @Valid UserJoinRequestDto requestDto,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()){
            return "join";
        }

        Boolean isDuplicate = userService.userDuplicateCheck(requestDto.getNickname());
        if(!isDuplicate) {
            return "join";
        }

        userService.userJoin(requestDto);
        return "redirect:/user/login";
    }


    @PostMapping("/api/user/duplicate")
    @ResponseBody
    public boolean userDuplicate(
            @RequestBody String nickname
    ) {
        Boolean result = userService.userDuplicateCheck(nickname);
        return result;
    }

}
