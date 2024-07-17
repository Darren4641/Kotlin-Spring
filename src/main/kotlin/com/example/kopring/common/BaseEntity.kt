package com.example.kopring.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity (
    @CreatedDate
    @Column(nullable = false)
    var createdDate : LocalDateTime,

    @LastModifiedDate
    @Column(nullable = false)
    var updatedDate : LocalDateTime
) {
    constructor() : this (
        createdDate = LocalDateTime.now(),
        updatedDate = LocalDateTime.now()
    )
}