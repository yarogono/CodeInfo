package com.hanghae.codeinfo.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptDecryptionTest {

    @Value("${jasypt.encryptor.password}")
    private String jasyptKey;

    private final StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();

    @BeforeEach
    void before() {
        jasypt.setPassword(jasyptKey);
        jasypt.setAlgorithm("PBEWithMD5AndDES");
    }

    @Test
    void encrypt() {
        String result = jasypt.encrypt("test");
        System.out.println(result);
    }


    @Test
    void decrypt() {

        String result = jasypt.decrypt("5mmK/IDPL6RK1DwvYCfsnw==");
        System.out.println(result);
    }
}
