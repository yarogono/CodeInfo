package com.hanghae.codeinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {

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
