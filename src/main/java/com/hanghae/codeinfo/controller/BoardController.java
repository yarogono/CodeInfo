package com.hanghae.codeinfo.controller;


import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.service.BoardService;
import com.hanghae.codeinfo.utils.ViewCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public BoardController(BoardRepository boardRepository, BoardService boardService) {
        this.boardRepository = boardRepository;
        this.boardService = boardService;
    }


    // 메인페이지
    @GetMapping("/")
    public String boardListPage(Model model) {
        boardService.getBoardListDesc(0, model);
        return "boardList";
    }

    @GetMapping("/{page}")
    public String boardListPaging(@PathVariable int page, Model model) {
        boardService.getBoardListDesc(page, model);
        return "boardList";
    }



    // 게시글 상세 페이지
    @GetMapping("/detail/{id}")
    public String boardDetailPage(@PathVariable Long id, Model model, HttpServletRequest request,
                                    HttpServletResponse response) {
        Optional<Board> result = boardRepository.findById(id);
        Board board = result.get();
        ViewCount viewCount = new ViewCount(boardService, boardRepository);
        viewCount.viewCookieCheck(id, request, response, board);
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
        boardService.upload(form);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Long deletePost(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }

    @PutMapping("/detail/{id}")
    public String updateNotice(@PathVariable Long id, BoardForm form) {
        boardService.update(id, form);
        return "redirect:/";
    }

}
