package com.example.kopring.Member.infrastructure

import com.example.kopring.Member.entity.Member
import com.example.kopring.Member.repository.MemberRepository
import com.example.kopring.Member.service.MemberReader
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class MemberReaderImpl(
    private val memberRepository: MemberRepository
) : MemberReader {

    override fun getMemberById(id: String): Member? = memberRepository.findByIdOrNull(id)?.let { return it }

    override fun isRegisteredMember(email: String): Boolean = memberRepository.existsByEmail(email)

}