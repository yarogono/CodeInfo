package com.hanghae.codeinfo.controller;


import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나, @NotNull 이 붙은 필드에대해 생성자를 생성해줍니다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해 사용
@Controller
public class BoardController {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
    }


    @GetMapping("/")
    public String boardListPage(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boardList", boards);
        return "boardList";
    }

    @GetMapping("/detail/{id}")
    public String boardDetailPage(@PathVariable Long id, Model model) {
        Optional<Board> result = boardRepository.findById(id);
        Board boad = null;
        Board board = result.orElse(boad);
        model.addAttribute("board", board);

        return "boardDetail";
    }


    @GetMapping("/upload")
    public String boardUploadPage(Model model) {
        return "boardUpload";
    }

    @PostMapping("/upload")
    public String uploadNotice(BoardForm form) {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        board.setContent(form.getContent());
        boardRepository.save(board);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Long deletePost(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return id;
    }
}
