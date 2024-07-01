package kr.co.testcode.model.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class UserCreateDTO(
    val email: String,
    val nickname: String,
    val address: String
) {

}