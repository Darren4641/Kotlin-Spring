package com.example.kopring.security.service

import com.example.kopring.Member.repository.MemberRepository
import com.example.kopring.security.oauth2.OAuth2UserInfo
import com.example.kopring.security.oauth2.OAuth2UserInfoFactory
import com.example.kopring.security.util.ProviderType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.util.*

@Component
class OAuth2UserService (
    private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User? {
        val user = super.loadUser(userRequest)
        return try {
            this.process(userRequest, user)
        } catch (ex: AuthenticationException) {
            throw AlreadyValueException(exceptionMessage("message.already.id"))
        }
    }

    private fun process(userRequest: OAuth2UserRequest, user: OAuth2User): OAuth2User {
        val providerType: ProviderType = ProviderType.valueOf(
            userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault())
        )
        val userInfo: OAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.attributes)
        val userId: String = providerType.toString() + "_" + userInfo.getEmail()
        val roles: MutableSet<Role> = HashSet<Role>()
        val userRole: Role = userService.createRoleIfNotFound(RoleType.USER)
        roles.add(userRole)
        var savedUser: User? = userService.getUser(userId)
        if (savedUser != null) {
            if (providerType !== savedUser.getProviderType()) {
                throw OAuthProviderMissMatchException(
                    ("Looks like you're signed up with " + providerType
                            + " account. Please use your " + savedUser.getProviderType()).toString() + " account to login."
                )
            }
            updateUser(savedUser, userInfo)
        } else {
            savedUser = createUser(userInfo, providerType, userId, roles)
        }
        return UserPrincipal.create(savedUser, user.attributes)
    }
}