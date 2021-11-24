package com.hanghae.codeinfo.controller;


import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.service.BoardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나, @NotNull 이 붙은 필드에대해 생성자를 생성해줍니다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해 사용
@Controller
public class BoardController {

    private final BoardService boardService;
    
    // @Autowired 어노테이션을 활용한 의존성 주입
    @Autowired
    public BoardController(
            BoardService boardService
    ) {
        this.boardService = boardService;
    }


    // 메인페이지
    @GetMapping("/")
    public String boardListPage(Model model) {
        boardService.getBoardListDesc(0, model);
        return "boardList";
    }

    // 메인페이지 페이징
    @GetMapping("/{page}")
    public String boardListPaging(
            @PathVariable int page,
            Model model
    ) {
        boardService.getBoardListDesc(page, model);
        return "boardList";
    }



    // 게시글 상세 페이지
    @GetMapping("/detail/{id}")
    public String boardDetailPage(
            @PathVariable Long id,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Board board = boardService.viewCountUpAndCookieCheck(id, request, response);
        model.addAttribute("board", board);

        return "boardDetail";
    }


    // 게시글 업로드 페이지
    @GetMapping("/upload")
    public String boardUploadPage() {
        return "boardUpload";
    }

    // 게시글 업로드
    @PostMapping("/upload")
    public String boardUpload(BoardRequestDto requestDto) {
        boardService.upload(requestDto);
        return "redirect:/";
    }


    // 게시글 삭제
    @DeleteMapping("/{id}")
    @ResponseBody
    public Long deletePost(@PathVariable Long id) {
        boardService.delete(id);
        return id;
    }


    // 게시글 수정
    @PutMapping("/detail/{id}")
    public String boardUpdate(
            @PathVariable Long id,
            BoardRequestDto requestDto
    ) {
        boardService.update(id, requestDto);
        return "redirect:/";
    }

}
