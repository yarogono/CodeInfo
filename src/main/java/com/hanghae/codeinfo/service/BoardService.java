package com.hanghae.codeinfo.service;

import com.hanghae.codeinfo.domain.Board;
import com.hanghae.codeinfo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long update(Long id, Board board) {
        Board result = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        result.update(board);
        return id;
    };
//    private final ProductRepository productRepository;
//
//    @Transactional
//    public Long update(Long id, ProductMypriceRequestDto requestDto) {
//        Product product = productRepository.findById(id).orElseThrow(
//                () -> new NullPointerException("아이디가 존재하지 않습니다.")
//        );
//        product.update(requestDto);
//        return id;
//    }
}
