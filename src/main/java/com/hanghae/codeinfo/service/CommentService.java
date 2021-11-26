package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.domain.Comment;
import com.hanghae.codeinfo.dto.CommentRequestDto;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.repository.CommentRepository;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }


    public void addComment(CommentRequestDto requestDto, Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find "));

        Comment comment = Comment.builder()
                .writer(requestDto.getWriter())
                .content(requestDto.getContent())
                .board(board)
                .build();

       commentRepository.save(comment);
    }

    public List<Comment> findAllComments(Board board) {
        Sort.Direction direction = Sort.Direction.DESC;

        Sort sort = Sort.by(direction, "commentId");
        Pageable pageable = PageRequest.of(0, 15, sort);
        List<Comment> comments = commentRepository.findAllByBoard(board, pageable);

        return comments;
    }

    public void deleteComment(
            Long commentId,
            UserDetailsImpl userDetails
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if(!userDetails.getUsername().equals(comment.getWriter())) {
            return;
        }
        commentRepository.deleteById(commentId);
    }
}
