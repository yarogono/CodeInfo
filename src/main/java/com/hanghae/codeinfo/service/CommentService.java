package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.dto.CommentRequestDto;
import com.hanghae.codeinfo.exception.ExceptionMessages;
import com.hanghae.codeinfo.model.Board;
import com.hanghae.codeinfo.model.Comment;
import com.hanghae.codeinfo.repository.BoardRepository;
import com.hanghae.codeinfo.repository.CommentRepository;
import com.hanghae.codeinfo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void addComment(CommentRequestDto requestDto, Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(
                        () -> new UsernameNotFoundException(ExceptionMessages.BOARD_IS_NULL)
                );

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
        Comment comment = findCommentByBoardId(commentId);

        if(!userDetails.getUsername().equals(comment.getWriter())) {
            return;
        }

        commentRepository.deleteById(commentId);
    }

    public void updateComment(CommentRequestDto requestDto) {

        Comment comment = findCommentByBoardId(requestDto.getCommentId());

        comment.update(requestDto);

        commentRepository.save(comment);
    }

    private Comment findCommentByBoardId(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(
                        () -> new NullPointerException(ExceptionMessages.COMMENT_IS_NULL)
                );

        return comment;
    }
}
