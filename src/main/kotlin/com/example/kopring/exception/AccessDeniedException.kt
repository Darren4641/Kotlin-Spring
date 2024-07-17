package com.example.kopring.exception

import com.example.kopring.common.status.ResultCode

class AccessDeniedException(
    resultCode: ResultCode
) : ServiceException(resultCode)