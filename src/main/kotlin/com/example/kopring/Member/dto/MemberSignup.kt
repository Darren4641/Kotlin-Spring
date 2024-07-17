package com.example.kopring.Member.dto

import com.example.kopring.Member.entity.Member
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

data class MemberSignupReq (
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
    val email : String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[!@#\$%^&*(),.?\":{}|<>]).{6,}\$", message = "비밀번호는 대/소문자 6글자 이상, 특수문자 1개 포함입니다.")
    var password : String,
    @field:NotBlank
    @field:Size(min = 1, max = 5, message = "이름은 1자 이상 5자 이하여야 합니다.")
    val name : String,
    @field:NotBlank
    @field:Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "생일 형식이 맞지 않습니다. (예: YYYY-MM-DD)")
    val birth : String
) {
    constructor(member : Member) : this (
        email = member.email,
        name = member.name,
        password = member.password,
        birth = member.birth
    )

    fun toServiceDto() : MemberSignupReqService {
        return MemberSignupReqService(email, password, name, birth)
    }

    fun encPassword(passwordEncoder : PasswordEncoder) {
        password = passwordEncoder.encode(password)
    }
}

data class MemberSignupReqService (
    val email : String,
    val password : String,
    val name : String,
    val birth : String
) {
    constructor(memberSignupReq : MemberSignupReq) : this (
        email = memberSignupReq.email,
        password = memberSignupReq.password,
        name = memberSignupReq.name,
        birth = memberSignupReq.birth
    )

    fun toEntity() : Member {
        return Member(email, password, name, birth)
    }
}

data class MemberSignRes (
    val id : String,
    val email : String,
    val name : String,
    val birth : String,
    val createdDate : LocalDateTime,
    val updatedDate : LocalDateTime
    ) {
    constructor(member : Member) : this (
        id = member.id,
        email = member.email,
        name = member.name,
        birth = member.birth,
        createdDate = member.createdDate,
        updatedDate = member.updatedDate
    )
}


