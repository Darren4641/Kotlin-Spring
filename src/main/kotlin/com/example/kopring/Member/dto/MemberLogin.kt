package com.example.kopring.Member.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class MemberLoginReq (
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
    val email : String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[!@#\$%^&*(),.?\":{}|<>]).{6,}\$", message = "비밀번호는 대/소문자 6글자 이상, 특수문자 1개 포함입니다.")
    var password : String
)


data class MemberLoginRes (
    var token : String,
    var tokenType : String
)


