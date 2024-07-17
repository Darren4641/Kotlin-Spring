package com.example.kopring.Member.infrastructure

import com.example.kopring.Member.dto.MemberSignRes
import com.example.kopring.Member.dto.MemberSignupReqService
import com.example.kopring.Member.entity.Member
import com.example.kopring.Member.repository.MemberRepository
import com.example.kopring.Member.service.MemberStore
import org.springframework.stereotype.Component


@Component
class MemberStoreImpl(
    private val memberRepository: MemberRepository
) : MemberStore {

    override fun registerMember(memberSignupReqService: MemberSignupReqService): MemberSignRes? {
        val member = memberRepository.save(memberSignupReqService.toEntity())
        return MemberSignRes(member)
    }

}