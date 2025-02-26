package kr.co.testcode.controller

import kr.co.testcode.exception.CertificationCodeNotMatchedException
import kr.co.testcode.exception.ResourceNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
        ResourceNotFoundException::class
    )
    fun resourceNotFoundException(exception: ResourceNotFoundException): String {
        return exception.message!!
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(
        CertificationCodeNotMatchedException::class
    )
    fun certificationCodeNotMatchedException(exception: CertificationCodeNotMatchedException): String {
        return exception.message!!
    }
}