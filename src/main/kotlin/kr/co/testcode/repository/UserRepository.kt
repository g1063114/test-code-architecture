package kr.co.testcode.repository

import kr.co.testcode.model.UserStatus
import kr.co.testcode.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByIdAndStatus(id: Long, userStatus: UserStatus): UserEntity?

    fun findByEmailAndStatus(email: String, userStatus: UserStatus): UserEntity?
}