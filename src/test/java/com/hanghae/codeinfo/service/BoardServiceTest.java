package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@Transactional
@Rollback
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;


    @Test
    void test() {

        Board board = Board.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        boardRepository.save(board);

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                        .title("testTitle")
                                        .content("testContent")
                                        .build();

        boardService.boardUpdate(board.getPostId(), boardRequestDto);
    }

}