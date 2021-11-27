package com.hanghae.codeinfo.controller;

import com.hanghae.codeinfo.dto.CommentRequestDto;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import com.hanghae.codeinfo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("/detail/{id}")
    public void addComment(
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long id
    ) {
        commentService.addComment(requestDto, id);
    }

    @DeleteMapping("/api/comment")
    public void deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody Long commentId
    ) {
        commentService.deleteComment(commentId, userDetails);
    }


    @PutMapping("/api/board/comment")
    public void updateComment(
            @RequestBody CommentRequestDto requestDto
    ) {
        commentService.updateComment(requestDto);
    }
}
