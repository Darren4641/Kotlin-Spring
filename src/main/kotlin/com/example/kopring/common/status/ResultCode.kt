package com.example.kopring.common.status


enum class ResultCode (
    val code : String,
    val message : String
) {
    SUCCESS("D-0", "OK"),
    ERROR("D-99", "ERROR"),
    INVALID_PARAMETER("D-01", "입력값이 올바르지 않습니다."),
    MEMBER_ALREADY("D-100", "이미 회원가입된 계정입니다."),
    INVALID_MEMBER_INFO("D-101", "ID/PW 가 올바르지 않습니다."),

    ILLEGAL_TOKEN_ERROR("D-995", "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN_ERROR("D-996", "토큰이 만료되었습니다."),
    EXPIRED_TOKEN_ERROR("D-997", "토큰이 만료되었습니다."),
    INVALID_TOKEN_ERROR("D-998", "토큰이 올바르지 않습니다."),
    SECURITY_ERROR("D-999", "인증에 실패하였습니다.")

}