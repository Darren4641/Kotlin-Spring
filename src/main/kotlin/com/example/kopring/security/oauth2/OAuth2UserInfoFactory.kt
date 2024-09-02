package com.example.kopring.security.oauth2

import com.example.kopring.security.oauth2.impl.AppleOAuth2UserInfo
import com.example.kopring.security.util.ProviderType

class OAuth2UserInfoFactory {

    companion object {
        fun getOAuth2UserInfo(providerType: ProviderType?, attributes: Map<String, *>): OAuth2UserInfo? {
            return when (providerType) {

                ProviderType.APPLE -> AppleOAuth2UserInfo(attributes)
//          GOOGLE -> GoogleOAuth2UserInfo(attributes)
//          FACEBOOK -> FacebookOAuth2UserInfo(attributes)
//          NAVER -> NaverOAuth2UserInfo(attributes)
//          KAKAO -> KakaoOAuth2UserInfo(attributes)
                else -> throw IllegalArgumentException("Invalid Provider Type.")
            }
        }
    }

}