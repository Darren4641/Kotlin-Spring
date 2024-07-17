package com.example.kopring.exception.handler

import com.example.kopring.common.status.ResultCode
import com.example.kopring.exception.ServiceException
import com.example.kopring.exception.dto.ExceptionMsg
import com.example.kopring.exception.dto.FieldErrorDetail
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.util.function.Consumer


@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(ServiceException::class)
    fun serviceException(
        ex : ServiceException
    ) : ResponseEntity<ExceptionMsg> {
        val temp = ResponseEntity(
            ExceptionMsg(
                code = ex.resultCode.code ,
                message = ex.resultCode.message,
                success = false,
                errors = emptyList()
            ),
            HttpStatus.BAD_REQUEST
        )
        println(temp)
        return temp
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodValidException (
        ex : MethodArgumentNotValidException
    ) : ResponseEntity<ExceptionMsg> {
        val errors: MutableList<FieldErrorDetail> = ArrayList<FieldErrorDetail>()
        println("PARAMS Exception")
        ex.bindingResult.allErrors.forEach(Consumer { error : ObjectError ->
            errors.add(
                FieldErrorDetail(
                    field = (error as FieldError).field,
                    message = error.defaultMessage ?: "Invalid Params"
                )
            )
        })

        return ResponseEntity(
            ExceptionMsg(
                code = ResultCode.INVALID_PARAMETER.code,
                message = ResultCode.INVALID_PARAMETER.message,
                success = false,
                errors = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: WebRequest
    ): ResponseEntity<ExceptionMsg> {
        val rootCause = ex.cause

        val errors : MutableList<FieldErrorDetail> = ArrayList<FieldErrorDetail>()
        if (rootCause is InvalidFormatException) {
            for (reference in rootCause.path) {
                errors.add(
                    FieldErrorDetail(
                        field = reference.fieldName,
                        message = "[" + reference.fieldName + "] 가 타입이 알맞지 않습니다."
                    )
                )
            }
        } else if (rootCause is JsonMappingException) {
            for (reference in rootCause.path) {
                errors.add(
                    FieldErrorDetail(
                        field = reference.fieldName,
                        message = "[" + reference.fieldName + "] 가 누락되었습니다."
                    )
                )
            }
        } else {
            errors.add(
                FieldErrorDetail(
                field = "",
                message = rootCause?.message?:"에러 발생"
            ))
        }
        return ResponseEntity(
            ExceptionMsg(
                code = ResultCode.INVALID_PARAMETER.code,
                message = ResultCode.INVALID_PARAMETER.message,
                success = false,
                errors = errors
            ),
            HttpStatus.BAD_REQUEST
        )
    }

}