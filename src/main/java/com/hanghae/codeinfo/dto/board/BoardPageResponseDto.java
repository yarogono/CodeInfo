package com.hanghae.codeinfo.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardPageResponseDto {
    private int curPage;
    private int prevPage;
    private int nextPage;
    private int totalPage;
}
