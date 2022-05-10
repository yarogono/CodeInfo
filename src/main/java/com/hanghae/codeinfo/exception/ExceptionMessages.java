package com.hanghae.codeinfo.exception;

public final class ExceptionMessages {

    // User
    public static String NICKNAME_AND_PWD_SAME = "비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.";
    public static String PWD_ARE_NOT_SAME = "비밀번호가 같지 않습니다.";
    public static String NICKNAME_DUPLICATE = "중복된 닉네임입니다.";
    public static String ILLEGAL_NICKNAME_PWD = "닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.";
    public static String ILLEGAL_PWD_LENGTH = "비밀번호는 4자이상의 비밀번호여야 합니다.";
    public static String USERDETAILS_IS_NULL = "로그인이 필요합니다.";

    public static String ALLREADY_LOGIN = "이미 로그인 되어있습니다";


    // Board
    public static String BOARD_IS_NULL = "게시글이 없습니다.";

    // Comment
    public static String COMMENT_IS_NULL = "해당 댓글이 없습니다.";
}
