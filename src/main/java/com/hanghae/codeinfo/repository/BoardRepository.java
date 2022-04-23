package com.hanghae.codeinfo.repository;

import com.hanghae.codeinfo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update Board b set b.title = :title b.content = :content where b.boardId = :id")
    void updateBoard(@Param("id") Long id,
                    @Param("title") String title,
                    @Param("content") String content);
}