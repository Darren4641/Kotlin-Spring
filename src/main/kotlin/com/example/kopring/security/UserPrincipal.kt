package com.example.kopring.security

import com.example.kopring.Member.entity.Member
import com.example.kopring.security.util.ProviderType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.core.oidc.OidcUserInfo
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrincipal (
    @get:JvmName("userEmail")
    val email : String,
    val providerType: ProviderType,
    val roles : Set<String>,
    @get:JvmName("getUserAttributes")
    val attributes: MutableMap<String, Any> = mutableMapOf<String, Any>()
) : OAuth2User, OidcUser, UserDetails {

    constructor(member: Member, providerType: ProviderType) : this(
        email = member.email,
        providerType = providerType,
        roles = member.roles.split(",").toSet(),
    )

    constructor(member: Member, providerType: ProviderType, attributes: MutableMap<String, Any>) : this(
        email = member.email,
        providerType = providerType,
        roles = member.roles.split(",").toSet(),
        attributes = attributes
    )

    override fun getName(): String {
        return email
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.map { GrantedAuthority { it.trim() } }.toMutableSet()
    }

    override fun getClaims(): MutableMap<String, Any> {
        return mutableMapOf<String, Any>()
    }

    override fun getUserInfo(): OidcUserInfo? {
        return null
    }

    override fun getIdToken(): OidcIdToken? {
        return null
    }


    override fun getPassword(): String {
        return "NO_PASS"
    }


    override fun getUsername(): String {
        return email
    }

}