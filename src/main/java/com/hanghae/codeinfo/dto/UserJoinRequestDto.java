package com.hanghae.codeinfo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserJoinRequestDto {

    @NotBlank(message="이름이 비어있습니다.")
    @Size(min = 3, max = 15, message = "3 ~ 15자리")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{3,}$",
             message = "영어 대소문자 각각 1개, 숫자 포함 형태의 3~15자리입니다.")
    private String nickname;

    @Size(min = 4, max = 15)
    @NotBlank(message = "비밀번호가 비어있습니다.")
    private String password;

    @Size(min = 4, max = 15)
    @NotBlank(message = "비밀번호 확인이 비어있습니다.")
    private String password2;
}
