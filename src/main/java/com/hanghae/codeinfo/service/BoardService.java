package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.model.Board;
import com.hanghae.codeinfo.model.Comment;
import com.hanghae.codeinfo.dto.BoardRequestDto;
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


    public void getBoardListDesc(int page, Model model) {
        Pageable boardPage = PageRequest.of(page, 5, Sort.by("postId").descending());

        Page<Board> boardList = boardRepository.findAll(boardPage);

        model.addAttribute("boardList", boardList);

        int totalPages = boardList.getTotalPages();
        int curPage = boardList.getNumber();


        // ToDo: 로직을 수정해서 리팩토링 필요
        if(page == 0 && totalPages > 1) {
            model.addAttribute("nextPage", 1);
        } else {
            if(curPage > 0) {
                int prevPage = boardList.getNumber() - 1;
                model.addAttribute("prevPage", prevPage);
            }

            if(curPage < totalPages - 1) {
                int nextPage = boardList.getNumber() + 1;
                model.addAttribute("nextPage", nextPage);
            }
        }
    }

    public void boardUpload(
            BoardRequestDto requestDto,
            UserDetailsImpl userDetails
    ) {
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .writer(userDetails.getUsername())
                .content(requestDto.getContent())
                .build();

        boardRepository.save(board);
    }

    public void boardUpdate(Long id, BoardRequestDto requestDto) {
        Optional<Board> findBoard = boardRepository.findById(id);

        boardValidCheck(findBoard);

        Board board = findBoard.get();

        board.update(
                requestDto.getTitle(),
                requestDto.getContent()
        );
        boardRepository.save(board);
    };


    public void boardDelete(Long id) {
        Optional<Board> board = boardRepository.findById(id);

        boardValidCheck(board);

        boardRepository.deleteById(id);
    }



    public Board viewCountUpAndCookieCheck(
            Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Optional<Board> findBoard = boardRepository.findById(id);
        boardValidCheck(findBoard);

        Board board  = findBoard.get();

        // 클라이언트 쿠키 체크
        Cookie oldCookie =  clientCookieCheck(request);

        // 클라이언트가 이미 쿠키가 있으면 true
        if (oldCookie != null) {
            // 해당 게시글 방문여부 체크
            boolean hadVisitedCheck = oldCookie.getValue().contains("[" + id + "]");

            // 방문 하지 않았으면 쿠키에 value 추가, 뷰 카운트업
            if(!hadVisitedCheck) {
                oldCookieValueAdd(id, oldCookie, response);

                boardViewCountUp(board);
            }
        } else {
            // 새로운 쿠키 추가
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
        board.addViewCount(board);
        boardRepository.save(board);
    }

    public void boardViews(Long id, Model model, HttpServletRequest request, HttpServletResponse response) {
        Board board = viewCountUpAndCookieCheck(id, request, response);
        List<Comment> comments = commentService.findAllComments(board);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
    }

    private void boardValidCheck(Optional<Board> board) {
        if(!board.isPresent()) {
            throw new NullPointerException("게시글이 없습니다.");
        }
    }
}
