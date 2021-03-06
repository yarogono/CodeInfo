package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.board.BoardPageResponseDto;
import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.model.Board;
import com.hanghae.codeinfo.model.Comment;
import com.hanghae.codeinfo.dto.board.BoardRequestDto;
import com.hanghae.codeinfo.repository.BoardRepository;

import com.hanghae.codeinfo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentService commentService;


    public BoardPageResponseDto getBoardListDesc(int page, Model model) {
        Pageable boardPage = PageRequest.of(page, 5, Sort.by("postId").descending());

        Page<Board> boardList = boardRepository.findAll(boardPage);


        int totalPage = boardList.getTotalPages();
        int curPage = boardList.getNumber();
        int nextPage = curPage + 1;
        int prevPage = curPage - 1;


        return BoardPageResponseDto.builder()
                .totalPage(totalPage)
                .nextPage(nextPage)
                .prevPage(prevPage)
                .curPage(curPage)
                .boardList(boardList)
                .build();
    }

    public void saveBoard(
            BoardRequestDto requestDto,
            UserDetailsImpl userDetails
    ) {

        loginCheck(userDetails);

        Board board = Board.builder()
                .title(requestDto.getTitle())
                .writer(userDetails.getUsername())
                .content(requestDto.getContent())
                .build();

        boardRepository.save(board);
    }


    public void updateBoard(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails) {

        loginCheck(userDetails);

        Optional<Board> findBoard = boardRepository.findById(id);

        Board board = boardValidCheck(findBoard);

        Long boardId = board.getPostId();
        String boardTitle = board.getTitle();
        String boardContent = board.getContent();

        boardRepository.updateBoard(boardId, boardTitle, boardContent);
    };


    public void deleteBoard(Long id, UserDetailsImpl userDetails) {
        loginCheck(userDetails);

        Optional<Board> findBoard = boardRepository.findById(id);

        Board board = boardValidCheck(findBoard);

        boardRepository.deleteById(board.getPostId());
    }



    public Board viewCountUpAndCookieCheck(
            Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Optional<Board> findBoard = boardRepository.findById(id);
        Board board = boardValidCheck(findBoard);

        // ??????????????? ?????? ??????
        Cookie oldCookie =  clientCookieCheck(request);

        // ?????????????????? ?????? ????????? ????????? true
        if (oldCookie != null) {
            // ?????? ????????? ???????????? ??????
            boolean hadVisitedCheck = oldCookie.getValue().contains("[" + id + "]");

            // ?????? ?????? ???????????? ????????? value ??????, ??? ????????????
            if(!hadVisitedCheck) {
                oldCookieValueAdd(id, oldCookie, response);

                boardViewCountUp(board);
            }
        } else {
            // ????????? ?????? ??????
            giveNewCookie(id, response);

            boardViewCountUp(board);
        }
        return board;
    }

    private Cookie clientCookieCheck(HttpServletRequest request) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }
        return oldCookie;
    }

    private void oldCookieValueAdd(
            Long id,
            Cookie oldCookie,
            HttpServletResponse response
    ) {
        oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
        oldCookie.setPath("/");
        response.addCookie(oldCookie);
    }

    private void giveNewCookie(
            Long id,
            HttpServletResponse response
    ) {
        Cookie newCookie = new Cookie("postView","[" + id + "]");
        newCookie.setPath("/");
        newCookie.setMaxAge(60 * 60 * 24);
        response.addCookie(newCookie);
    }

    private void boardViewCountUp(Board board) {

        Long boardId = board.getPostId();
        int boardViews = board.getViews();

        boardRepository.viewCountUp(boardId, boardViews);
    }

    public void boardViews(Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Board board = viewCountUpAndCookieCheck(id, request, response);
        List<Comment> comments = commentService.findAllComments(board);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
    }

    private void loginCheck(UserDetailsImpl userDetails) {
        if(userDetails == null) {
            throw new NullPointerException(ExceptionMessages.USERDETAILS_IS_NULL);
        }
    }

    private Board boardValidCheck(Optional<Board> board) {
        if(board.isEmpty()) {
            throw new NullPointerException(ExceptionMessages.BOARD_IS_NULL);
        }
        return board.get();
    }
}
