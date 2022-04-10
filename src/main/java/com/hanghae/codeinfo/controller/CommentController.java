package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.dto.CommentRequestDto;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/board/{id}")
    public void addComment(
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long id
    ) {
        commentService.addComment(requestDto, id);
    }

    @DeleteMapping("/comment")
    public void deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Long commentId
    ) {
        commentService.deleteComment(commentId, userDetails);
    }


    @PutMapping("/board/comment")
    public void updateComment(
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.updateComment(requestDto);
    }
}
