package com.hanghae.codeinfo.domain;

import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter // get 함수를 일괄적으로 만들어줍니다.
@Setter
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


    public Board(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.writer = requestDto.getWriter();
    }
}
