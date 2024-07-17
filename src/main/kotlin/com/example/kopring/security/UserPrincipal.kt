package com.example.kopring.security

import com.example.kopring.Member.entity.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal (
    val email : String,
    val pwd : String,
    val roles : Set<String>

) : UserDetails {

    constructor(member: Member) : this(
        email = member.email,
        pwd = member.password,
        roles = member.roles.split(",").toSet(),
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { GrantedAuthority { it.trim() } }.toMutableSet()
    }

    override fun getPassword(): String {
        return pwd
    }


    override fun getUsername(): String {
        return email
    }


}