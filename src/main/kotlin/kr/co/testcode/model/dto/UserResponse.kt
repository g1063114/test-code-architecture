package kr.co.testcode.model.dto

import kr.co.testcode.model.UserStatus

class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val status: UserStatus,
    val lastLoginAt: Long? = null
) {
}