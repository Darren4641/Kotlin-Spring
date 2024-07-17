package com.example.kopring.security.config

import com.example.kopring.security.filter.TokenAuthenticationFilter
import com.example.kopring.security.handler.AuthenticationEntryPointHandler
import com.example.kopring.security.handler.TokenAccessDeniedHandler
import com.example.kopring.security.service.UserDetailService
import com.example.kopring.security.token.AuthTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig (
    private val tokenProvider: AuthTokenProvider,
    private val userDetailService: UserDetailService,
    private val authenticationEntryPointHandler: AuthenticationEntryPointHandler,
    private val tokenAccessDeniedHandler: TokenAccessDeniedHandler
) {

    @Bean
    fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/v1/member/signup").permitAll()
                    .requestMatchers("/v1/member/login").permitAll()
                    .requestMatchers("/v1/**").authenticated()
                    .anyRequest().permitAll()
            }
            .formLogin{ formLogin -> formLogin.disable() }
            .httpBasic{ httpBasic -> httpBasic.disable() }
            .exceptionHandling{
                ex ->
                //인증 되지 않은 사용자 접근시 핸들러 -401
                ex.authenticationEntryPoint(authenticationEntryPointHandler)
                //인증은 되었으나, 권한이 없는경우 - 403
                ex.accessDeniedHandler(tokenAccessDeniedHandler)
            }
            .userDetailsService(userDetailService)
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)



        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter = TokenAuthenticationFilter(tokenProvider)

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager? = configuration.authenticationManager
}