package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.dto.UserRequestDto;
import com.hanghae.codeinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.NoSuchAlgorithmException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/join")
    public String joinPage() {
        return "join";
    }

    @PostMapping("/user/join")
    public String userJoin(UserRequestDto requestDto) throws NoSuchAlgorithmException {
        userService.userJoin(requestDto);
        return "redirect:/login";
    }

    @PostMapping("/api/user/duplicate")
    public String userDup() {

        return "redirct:/join";
    }
}
