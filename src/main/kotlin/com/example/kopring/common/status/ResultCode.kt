package com.example.kopring.common.status


enum class ResultCode (
    val code : String,
    val message : String
) {
    SUCCESS("C-0", "OK"),
    ERROR("C-99", "ERROR"),
    INVALID_PARAMETER("C-01", "입력값이 올바르지 않습니다."),
    MEMBER_ALREADY("C-100", "이미 회원가입된 계정입니다."),
    INVALID_MEMBER_INFO("C-101", "ID/PW 가 올바르지 않습니다."),

    ILLEGAL_TOKEN_ERROR("C-995", "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_ERROR("C-996", "토큰이 만료되었습니다."),
    EXPIRED_TOKEN_ERROR("C-997", "토큰이 만료되었습니다."),
    INVALID_TOKEN_ERROR("C-998", "토큰이 올바르지 않습니다."),
    SECURITY_ERROR("C-999", "인증에 실패하였습니다.")

}