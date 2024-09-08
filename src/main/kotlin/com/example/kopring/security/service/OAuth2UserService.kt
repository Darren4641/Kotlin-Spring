package com.example.kopring.security.service


import com.dopaminedefense.dodiserver.security.oauth2.OAuth2UserInfo
import com.dopaminedefense.dodiserver.security.oauth2.OAuth2UserInfoFactory
import com.example.kopring.Member.entity.Member
import com.example.kopring.Member.repository.MemberRepository
import com.example.kopring.security.UserPrincipal
import com.example.kopring.security.util.ProviderType
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class OAuth2UserService (
    private val memberRepository: MemberRepository
) : DefaultOAuth2UserService() {
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User? {
        val providerType: ProviderType = ProviderType.valueOf(
            userRequest.clientRegistration.registrationId.uppercase(Locale.getDefault()))

        lateinit var oauth2UserInfo: OAuth2UserInfo
        lateinit var attributes: Map<String, *>
        if(providerType == ProviderType.APPLE) {
            val idToken = userRequest.additionalParameters.get("id_token").toString()
            attributes = decodeJwtTokenPayload(idToken)
        } else {
            val user = super.loadUser(userRequest)
            attributes = user.attributes
        }
        oauth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, attributes)
        val loginUser = signUpIfNotExist(providerType, oauth2UserInfo)

        return UserPrincipal(
            member = loginUser,
            providerType = providerType,
            attributes = attributes as MutableMap<String, Any>
        )

    }


    private fun signUpIfNotExist(providerType: ProviderType, oauth2UserInfo: OAuth2UserInfo) : Member {
        lateinit var member: Member

        val email = oauth2UserInfo.getEmail()!!
        if(memberRepository.existsByEmail(email)) {
            member = memberRepository.findByEmail(email)!!
            member.signIn()
            memberRepository.save(member)
        } else {
            val localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val utcDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ssXXX")
            member = Member(
                email = email,
                password = "NO_PASS",
                name = oauth2UserInfo.getName() ?: "NO_NAME"
            )
            memberRepository.save(member)
        }
        return member
    }


    private fun decodeJwtTokenPayload(jwtToken: String) : Map<String, Objects> {
        val jwtClaims = HashMap<String, Objects>()

        try {
            val parts = jwtToken.split(".")
            val decoder = Base64.getUrlDecoder()
            val decodedBytes: ByteArray = decoder.decode(parts[1].toByteArray(Charsets.UTF_8))
            val decodedString = String(decodedBytes, Charsets.UTF_8)
            val mapper = ObjectMapper()

            val map = mapper.readValue(decodedString, Map::class.java) as Map<String, Objects>
            jwtClaims.putAll(map)
        } catch (e: JsonProcessingException) {
            logger.error("decodeJwtToken: {}-{} / jwtToken : {}", e.message, e.cause, jwtToken)
        }
        return jwtClaims
    }
}