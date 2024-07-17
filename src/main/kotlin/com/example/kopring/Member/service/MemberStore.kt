package com.example.kopring.Member.service

import com.example.kopring.Member.dto.MemberSignRes
import com.example.kopring.Member.dto.MemberSignupReqService
import com.example.kopring.Member.entity.Member

interface MemberStore {
    fun registerMember(memberSignupReqService : MemberSignupReqService): MemberSignRes?
}