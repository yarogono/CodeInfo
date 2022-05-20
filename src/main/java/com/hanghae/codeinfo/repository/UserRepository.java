package com.hanghae.codeinfo.repository;

import com.hanghae.codeinfo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    Optional<User> findByKakaoId(Long kakaoId);
}
