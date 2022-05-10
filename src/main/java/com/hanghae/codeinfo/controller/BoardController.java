package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.dto.BoardRequestDto;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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

    @GetMapping("/{page}")
    public String boardListPaging(
            @PathVariable int page,
            Model model
    ) {
        boardService.getBoardListDesc(page, model);
        return "boardList";
    }

    @GetMapping("/board/{id}")
    public String boardDetailPage(
            @PathVariable Long id,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.boardViews(id, model, request, response);
        if(userDetails != null) {
            model.addAttribute("username", userDetails.getUsername());
        }

        return "boardDetail";
    }

    @GetMapping("/board/upload")
    public String boardSavePage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            Model model
    ) {
        model.addAttribute("username", userDetails.getUsername());
        return "boardUpload";
    }


    @PostMapping("/board/upload")
    public String saveBoard(
            BoardRequestDto requestDto,
             @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        boardService.saveBoard(requestDto, userDetails);
        return "redirect:/";
    }

    @DeleteMapping("/board/{id}")
    @ResponseBody
    public Long deleteBoard(@PathVariable Long id, UserDetailsImpl userDetails) {
        boardService.deleteBoard(id, userDetails);
        return id;
    }

    @PutMapping("/board/update/{id}")
    public String updateBoard(
            @PathVariable Long id,
            BoardRequestDto requestDto,
            UserDetailsImpl userDetails
    ) {
        boardService.updateBoard(id, requestDto, userDetails);
        return "redirect:/";
    }

}
