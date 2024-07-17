package com.example.kopring.Member.service

import com.example.kopring.Member.dto.MemberSignRes
import com.example.kopring.Member.dto.MemberSignupReq

interface MemberService {
    fun signup(memberSignupReq: MemberSignupReq) : MemberSignRes?
}