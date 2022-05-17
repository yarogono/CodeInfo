package com.hanghae.codeinfo.repository;

import com.hanghae.codeinfo.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Transactional
    @Query("update Board b set b.title = :title, b.content = :content where b.postId = :id")
    void updateBoard(@Param("id") Long id,
                    @Param("title") String title,
                    @Param("content") String content);

    @Modifying
    @Transactional
    @Query("update Board b set b.views = :views + 1 where b.postId = :id")
    void viewCountUp(@Param("id") Long id,
                     @Param("views") int views);
}

