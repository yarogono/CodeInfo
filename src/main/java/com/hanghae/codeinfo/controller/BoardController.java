package com.hanghae.codeinfo.controller;


import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.dto.BoardRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나, @NotNull 이 붙은 필드에대해 생성자를 생성해줍니다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해 사용
@Controller
public class BoardController {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boardList", boards);
        return "index";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadNotice(BoardForm form) {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        boardRepository.save(board);
        return "redirect:/";
    }
}
