package com.example.kopring.Member.controller

import com.example.kopring.Member.dto.MemberLoginReq
import com.example.kopring.Member.dto.MemberLoginRes
import com.example.kopring.Member.dto.MemberSignRes
import com.example.kopring.Member.dto.MemberSignupReq
import com.example.kopring.Member.service.MemberService
import com.example.kopring.common.response.BaseResponse
import com.example.kopring.common.status.ResultCode
import com.example.kopring.exception.AccessDeniedException
import com.example.kopring.security.UserPrincipal
import com.example.kopring.security.token.AuthTokenProvider
import jakarta.validation.Valid
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/v1/member")
class MemberController(
    private val memberService : MemberService,
    private val passwordEncoder : PasswordEncoder,
    private val tokenProvider: AuthTokenProvider,
    private val authenticationManager : AuthenticationManager
) {

    @PostMapping("/signup")
    fun signup(
        @RequestBody
        @Valid
        memberSignupReq: MemberSignupReq) : BaseResponse<MemberSignRes> {
        memberSignupReq.encPassword(passwordEncoder)
        val memberRes = memberService.signup(memberSignupReq)
        val token = tokenProvider.createToken(memberSignupReq.email)
        return BaseResponse(data = memberRes)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody
        @Valid
        memberLoginReq: MemberLoginReq) : BaseResponse<MemberLoginRes> {
        val authentication = tryAuthentication(memberLoginReq)
        SecurityContextHolder.getContext().authentication = authentication


        val token = tokenProvider.createToken(
            id = memberLoginReq.email,
            roles = (authentication.principal as UserPrincipal).authorities)
        return BaseResponse(data = MemberLoginRes(token, "JWT"))
    }

    @GetMapping("/test")
    fun test() : String {
        return "test"
    }

    private fun tryAuthentication(memberLoginReq: MemberLoginReq) : Authentication {
        try {
            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(memberLoginReq.email, memberLoginReq.password))
        } catch (ex : Exception) {
            throw AccessDeniedException(ResultCode.INVALID_MEMBER_INFO)
        }
    }
}