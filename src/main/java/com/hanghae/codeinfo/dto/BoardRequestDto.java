package com.hanghae.codeinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@NoArgsConstructor
public class BoardRequestDto {
    private String title;
    private String writer;
    private String content;

    @Builder
    public BoardRequestDto(String title, String writer, String content) {
        Assert.notNull(title, "title가 null입니다.");
        Assert.notNull(writer, "writer가 null입니다.");
        Assert.notNull(content, "content가 null입니다.");

        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
