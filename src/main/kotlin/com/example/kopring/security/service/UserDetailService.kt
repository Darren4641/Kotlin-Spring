package com.example.kopring.security.service

import com.example.kopring.Member.entity.Member
import com.example.kopring.Member.repository.MemberRepository
import com.example.kopring.security.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component


@Component
class UserDetailService (
  private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val member : Member = memberRepository.findByEmail(email)
            .orElseThrow { throw UsernameNotFoundException("Can not find username.") }

        return UserPrincipal(member)
    }
}