package com.example.kopring.security.oauth2.impl

import com.example.kopring.security.oauth2.OAuth2UserInfo

class AppleOAuth2UserInfo(
    override val attributes: Map<String, *>
) : OAuth2UserInfo(attributes) {

    override fun getId(): String? {
        TODO("Not yet implemented")
    }

    override fun getName(): String? {
        TODO("Not yet implemented")
    }

    override fun getEmail(): String? {
        TODO("Not yet implemented")
    }

    override fun getImageUrl(): String? {
        TODO("Not yet implemented")
    }
}