package com.hanghae.codeinfo.dto;

import lombok.Getter;

@Getter
public class BoardRequestDto {
    private String title;
    private String writer;
    private String content;
    private int views;
}
