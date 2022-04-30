package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.model.Board;
import com.hanghae.codeinfo.model.User;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.repository.UserRepository;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@Transactional
@Rollback
@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    private Board board;
    private UserDetailsImpl userDetails;


    @BeforeEach
    void before() {
        board = Board.builder()
                .title("title")
                .content("content")
                .writer("writer")
                .build();

        boardRepository.save(board);

        userDetails = new UserDetailsImpl(User.builder()
                .nickname("testNick")
                .password("testPwd")
                .build()
        );

        userRepository.save(userDetails.getUser());
    }

    @Test
    @DisplayName("게시글 1개 저장")
    void 게시글_저장() {
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                        .title("testTitle")
                                        .content("testContent")
                                        .build();

        boardService.saveBoard(boardRequestDto, userDetails);
    }

    @Test
    @DisplayName("게시글 1개 삭제")
    void 게시글_삭제() {
        boardService.deleteBoard(board.getPostId());
    }

    @Test
    @DisplayName("게시글 1개 수정")
    void 게시글_수정() {
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                                        .title("modifyTitle")
                                                        .content("modifyContent")
                                                        .build();
        boardService.updateBoard(board.getPostId(), boardRequestDto);
    }

}