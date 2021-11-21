package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.controller.BoardForm;
import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
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

    @Transactional
    public void upload(BoardForm form) {
        Board board = Board.builder()
                .title(form.getTitle())
                .writer(form.getWriter())
                .content(form.getContent())
                .build();
        boardRepository.save(board);
    }

    @Transactional
    public void update(Long id, BoardForm form) {
        Board board = Board.builder()
                .title(form.getTitle())
                .writer(form.getWriter())
                .content(form.getContent())
                .build();

        Board result = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        result.update(board);
    };

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }


    @Transactional
    public void viewCountUp(Board board) {
        board.updateViews(board);
    }
}
