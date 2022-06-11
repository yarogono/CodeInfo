package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.board.BoardRequestDto;
import com.hanghae.codeinfo.exception.ExceptionMessages;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

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
    @DisplayName("게시글 1개 저장 / 성공")
    void 게시글_저장_성공() {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                        .title("testTitle")
                                        .content("testContent")
                                        .build();

        // when
        boardService.saveBoard(boardRequestDto, userDetails);

        // then
    }

    @Test
    @DisplayName("게시글 1개 저장 / 실패")
    void 게시글_저장_실패() {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                        .title("testTitle")
                                        .content("testContent")
                                        .build();

        userDetails = null;

        // when
        Exception exception = assertThrows(NullPointerException.class, () -> {
            boardService.saveBoard(boardRequestDto, userDetails);
        });

        // then
        assertEquals(ExceptionMessages.USERDETAILS_IS_NULL, exception.getMessage());
    }


    @Test
    @DisplayName("게시글 1개 삭제 / 성공")
    void 게시글_삭제_성공() {

        // when
        boardService.deleteBoard(board.getPostId(), userDetails);

        // then
    }

    @Test
    @DisplayName("게시글 1개 삭제 / 실패")
    void 게시글_삭제_실패() {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("testTitle")
                .content("testContent")
                .build();

        userDetails = null;

        // when
        Exception exception = assertThrows(NullPointerException.class, () -> {
            boardService.deleteBoard(board.getPostId(), userDetails);
        });

        // then
        assertEquals(ExceptionMessages.USERDETAILS_IS_NULL, exception.getMessage());
    }



    @Test
    @DisplayName("게시글 1개 수정 / 성공")
    void 게시글_수정_성공() {
        // given
        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                                                        .title("modifyTitle")
                                                        .content("modifyContent")
                                                        .build();

        // when
        boardService.updateBoard(board.getPostId(), boardRequestDto, userDetails);

        // then
    }

    @Test
    @DisplayName("게시글 1개 수정 / 실패")
    void 게시글_수정_실패() {

        BoardRequestDto boardRequestDto = BoardRequestDto.builder()
                .title("modifyTitle")
                .content("modifyContent")
                .build();

            userDetails = null;

        // when

        Exception exception = assertThrows(NullPointerException.class, () -> {
            boardService.updateBoard(board.getPostId(), boardRequestDto, userDetails);
        });

        // then
        assertEquals(ExceptionMessages.USERDETAILS_IS_NULL, exception.getMessage());
    }
}