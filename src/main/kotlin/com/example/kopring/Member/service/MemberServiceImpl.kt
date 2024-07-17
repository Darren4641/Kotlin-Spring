package com.example.kopring.Member.service

import com.example.kopring.Member.dto.MemberSignRes
import com.example.kopring.Member.dto.MemberSignupReq
import com.example.kopring.common.status.ResultCode
import com.example.kopring.exception.ServiceException
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl (
    private val memberReader : MemberReader,
    private val memberStore : MemberStore
) : MemberService {
    override fun signup(memberSignupReq: MemberSignupReq): MemberSignRes? {
        if(memberReader.isRegisteredMember(memberSignupReq.email)) {
            throw ServiceException(ResultCode.MEMBER_ALREADY)
        }
        return memberStore.registerMember(memberSignupReq.toServiceDto()).let { it }
    }
}
