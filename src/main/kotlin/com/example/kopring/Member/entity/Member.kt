package com.example.kopring.Member.entity

import com.example.kopring.common.BaseEntity
import com.example.kopring.security.RoleType
import jakarta.persistence.*
import org.springframework.cglib.core.Local
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*


@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "members")
class Member (
    @Id
    @Column(name = "id", nullable = false, length = 36)
    val id : String,

    @Column(name = "email", nullable = false, length = 320)
    var email : String,

    @Column(name = "password", nullable = false, length = 320)
    var password : String,

    @Column(name = "name", nullable = false, length = 320)
    var name : String,

    @Column(name = "birth", nullable = false, length = 10)
    var birth : String,

    @Column(name = "role", nullable = false, length = 255)
    var roles : String

) : BaseEntity() {

    constructor() : this(
        id = UUID.randomUUID().toString(),
        email = "",
        password = "",
        name = "",
        birth = "",
        roles = RoleType.USER.role
    )

    constructor(email: String, password : String, name: String, birth: String) : this (
        id = UUID.randomUUID().toString(),
        email = email,
        password = password,
        name = name,
        birth = birth,
        roles = RoleType.USER.role
    )

}