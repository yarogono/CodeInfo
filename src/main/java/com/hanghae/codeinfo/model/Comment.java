package com.hanghae.codeinfo.model;

import com.hanghae.codeinfo.dto.CommentRequestDto;
import com.hanghae.codeinfo.utils.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Board board;

    @Builder
    public Comment(String writer, String content, Board board) {
        this.writer = writer;
        this.content = content;
        this.board = board;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}

