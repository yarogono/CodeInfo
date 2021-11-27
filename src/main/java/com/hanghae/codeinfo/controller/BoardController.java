package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.domain.Comment;
import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.BoardService;
import com.hanghae.codeinfo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


//@RequiredArgsConstructor
// 초기화 되지 않은 final 필드나, @NotNull 이 붙은 필드에대해 생성자를 생성해줍니다.
// 주로 의존성 주입(Dependency Injection) 편의성을 위해 사용
@Controller
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;
    
    // @Autowired 어노테이션을 활용한 의존성 주입
    @Autowired
    public BoardController(
            BoardService boardService,
            CommentService commentService) {
        this.boardService = boardService;
        this.commentService = commentService;
    }


    // 메인페이지
    @GetMapping("/")
    public String boardListPage(
            Model model,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }
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
            HttpServletResponse response,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Board board = boardService.viewCountUpAndCookieCheck(id, request, response);
        List<Comment> comments = commentService.findAllComments(board);
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);

        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }

        return "boardDetail";
    }


    // 게시글 업로드 페이지
    @GetMapping("/upload")
    public String boardUploadPage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        model.addAttribute("username", userDetails.getUsername());
        return "boardUpload";
    }


    // 게시글 업로드
    @PostMapping("/api/board")
    public String boardUpload(
            BoardRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.upload(requestDto, userDetails);
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
