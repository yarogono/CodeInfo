package com.hanghae.codeinfo.dto.board;

import com.hanghae.codeinfo.model.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardPageResponseDto {
    private int curPage;
    private int prevPage;
    private int nextPage;
    private int totalPage;
    private Page<Board> boardList;
}
