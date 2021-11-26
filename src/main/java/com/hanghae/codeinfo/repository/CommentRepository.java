package com.hanghae.codeinfo.repository;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.domain.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
        List<Comment> findAllByBoard(Board board, Pageable pageable);
}