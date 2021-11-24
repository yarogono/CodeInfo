package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.repository.BoardRepository;

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


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    public void getBoardListDesc(int page, Model model) {
        Pageable boardPage = PageRequest.of(page, 5, Sort.by("no").descending());
        Page<Board> boardList = boardRepository.findAll(boardPage);

        model.addAttribute("boardList", boardList);

        int totalPages = boardList.getTotalPages();
        int curPage = boardList.getNumber();


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

    public void upload(BoardRequestDto requestDto) {
        Board board = Board.builder()
                .title(requestDto.getTitle())
                .writer(requestDto.getWriter())
                .content(requestDto.getContent())
                .build();
        boardRepository.save(board);
    }

    public void update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id)
                .orElse(null);

        board.update(
                requestDto.getTitle(),
                requestDto.getWriter(),
                requestDto.getContent()
        );
        boardRepository.save(board);
    };


    public void delete(Long id) {
        boardRepository.deleteById(id);
    }



    public Board viewCountUpAndCookieCheck(
            Long id,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Board board = boardRepository.findById(id)
                .orElse(null);

        // 클라이언트 쿠키 체크
        Cookie oldCookie =  clientCookieCheck(request);

        // 클라이언트가 이미 쿠키가 있으면 true
        if (oldCookie != null) {
            // 해당 게시글 방문여부 체크
            boolean hadVisitedCheck = oldCookie.getValue().contains("[" + id + "]");

            // 방문 하지 않았으면 쿠키에 value 추가, 뷰 카운트업
            if(!hadVisitedCheck) {
                oldCookieValueAdd(id, oldCookie, response);

                viewCountUp(board);
            }
        } else {
            // 새로운 쿠키 추가
            giveNewCookie(id, response);

            viewCountUp(board);
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

    private void viewCountUp(Board board) {
        board.addViewCount(board);
        boardRepository.save(board);
    }
}
