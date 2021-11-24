package com.hanghae.codeinfo.domain;


import com.hanghae.codeinfo.utils.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class User extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;


    @Builder
    public User(String nickname, String password) {
        Assert.notNull(nickname, "nickname이 null입니다.");
        Assert.notNull(password, "password가 null입니다.");

        this.nickname = nickname;
        this.password = password;
    }
}
