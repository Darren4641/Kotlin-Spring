package com.example.kopring.exception

import com.example.kopring.common.status.ResultCode

open class ServiceException(
    val resultCode: ResultCode
) : RuntimeException(resultCode.message)