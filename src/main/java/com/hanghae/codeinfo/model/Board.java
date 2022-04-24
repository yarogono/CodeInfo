package com.hanghae.codeinfo.model;

import com.hanghae.codeinfo.utils.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class Board extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 0")
    private int views;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Board(String title, String writer, String content, int views) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.views = views;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addViewCount(Board board) {
        this.views = board.getViews() + 1;
    }


}
