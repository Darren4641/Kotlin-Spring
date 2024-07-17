package com.example.kopring.Member.service

import com.example.kopring.Member.entity.Member

interface MemberReader {
    fun getMemberById(id : String) : Member?
    fun isRegisteredMember(email : String) : Boolean

}