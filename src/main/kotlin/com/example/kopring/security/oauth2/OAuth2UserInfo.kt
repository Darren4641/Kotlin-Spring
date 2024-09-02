package com.example.kopring.security.oauth2

abstract class OAuth2UserInfo (
    open val attributes: Map<String, *>?
) {

    fun getAttributes(): Map<String, *>? {
        return attributes
    }

    abstract fun getId(): String?

    abstract fun getName(): String?

    abstract fun getEmail(): String?

    abstract fun getImageUrl(): String?

}