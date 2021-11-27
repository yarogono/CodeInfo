package com.hanghae.codeinfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long commentId;
    private String writer;
    private String content;
}
