package com.hanghae.codeinfo.repository;

import com.hanghae.codeinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
