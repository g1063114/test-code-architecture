package kr.co.testcode.model.dto

import kr.co.testcode.model.UserStatus

class MyProfileResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val address: String,
    val status: UserStatus,
    val lastLoginAt: Long
) {
}