package com.hanghae.codeinfo.controller;


import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.service.BoardService;
import com.hanghae.codeinfo.utils.ViewCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나, @NotNull 이 붙은 필드에대해 생성자를 생성해줍니다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해 사용
@Controller
public class BoardController {

    private final BoardRepository boardRepository;

    private final BoardService boardService;
    
    // @Autowired 어노테이션을 활용한 의존성 주입
    @Autowired
    public BoardController(BoardRepository boardRepository, BoardService boardService, BoardService boardService1) {
        this.boardRepository = boardRepository;
        this.boardService = boardService1;
    }


    // 메인페이지
    @GetMapping("/")
    public String boardListPage(Model model) {
        List<Board> boards = boardRepository.findAll();
        Collections.sort(boards);
        model.addAttribute("boardList", boards);
        return "boardList";
    }



    // 게시글 상세 페이지
    @GetMapping("/detail/{id}")
    public String boardDetailPage(@PathVariable Long id, Model model, HttpServletRequest request,
                                    HttpServletResponse response) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.get();
        ViewCount viewCount = new ViewCount(boardService);
        viewCount.viewCountUp(id, request, response, board);
        model.addAttribute("board", board);
        return "boardDetail";
    }


    // 게시글 업로드 페이지
    @GetMapping("/post")
    public String boardUploadPage(Model model) {
        return "boardUpload";
    }

    @PostMapping("/post")
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


    @PutMapping("/detail/{id}")
    public String updateNotice(@PathVariable Long id, BoardForm form) {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        board.setContent(form.getContent());
        boardService.update(id, board);
        return "redirect:/";
    }

}
