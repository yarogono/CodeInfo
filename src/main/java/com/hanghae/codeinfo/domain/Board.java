package com.hanghae.codeinfo.domain;

import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.utils.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;


@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Board extends Timestamped {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long no;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0")
    private int views;


    @Builder
    public Board(String title, String writer, String content, int views) {
        Assert.notNull(title, "title가 null입니다.");
        Assert.notNull(writer, "writer가 null입니다.");
        Assert.notNull(content, "content가 null입니다.");

        this.title = title;
        this.writer = writer;
        this.content = content;
        this.views = views;
    }

    public void update(Board board) {
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.views = board.getViews();
    }

    public void updateViews(Board board) {
        this.title = board.getTitle();
        this.writer = board.getWriter();
        this.content = board.getContent();
        this.views = board.getViews() + 1;
    }


}
