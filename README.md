개발 환경 

| Spring Boot | 3.3.1 |
| --- | --- |
| Kotlin | 1.9.24 |
 

Spring Security 

src/main/com/example/kopring/security 하위 폴더에 시큐리티 파일 정의

# Custom 목록
[JWT 관련]
* UserDetailService
* TokenAuthenticationFilter
* AuthenticationEntryPointHandler
* TokenAccessDeniedHandler

[Oauth2 관련]
* OAuth2UserService
* OAuth2AuthenticationSuccessHandler
* CustomRequestEntityConverter


[2024/09/08]
<h3>Apple 로그인에 한해서만 Oauth2 적용 완료</h3>
application.yml파일에 각 소셜 정보 입력 후 securityConfig 흐름 대로 인증 인가 진행 </h3>
<h3>Oauth2 흐름도</h3>
Converter -> oauth2Service -> successHandler

각 커스팀 클래스에 oauth2 Factory 메서드 패턴을 이용해 소셜 별로 분기 처리 진행 완료

```java
class OAuth2UserInfoFactory {

    companion object {
        fun getOAuth2UserInfo(providerType: ProviderType?, attributes: Map<String, *>): OAuth2UserInfo {
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
```
위 코드와 같이 구글, 페이스북, 네이버, 카카오 등 필요에 따라 구현체 작성 요망


