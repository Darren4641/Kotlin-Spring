package com.example.kopring.Member.repository

import com.example.kopring.Member.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, String> {
    fun existsByEmail(email : String) : Boolean
    fun findByEmail(email : String) : Optional<Member>
}