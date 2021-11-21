package com.hanghae.codeinfo.utils;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.service.BoardService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewCount {

    private final BoardService boardService;

    private final BoardRepository boardRepository;

    public ViewCount(BoardService boardService, BoardRepository boardRepository) {
        this.boardService = boardService;
        this.boardRepository = boardRepository;
    }


    public void viewCookieCheck(Long id, HttpServletRequest request, HttpServletResponse response, Board board) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                boardService.viewCountUp(board);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            boardService.viewCountUp(board);
            Cookie newCookie = new Cookie("postView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
}
