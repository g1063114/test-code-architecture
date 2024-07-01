package kr.co.testcode.service

import kr.co.testcode.exception.CertificationCodeNotMatchedException
import kr.co.testcode.exception.ResourceNotFoundException
import kr.co.testcode.model.UserStatus
import kr.co.testcode.model.dto.UserCreateDTO
import kr.co.testcode.model.dto.UserUpdateDTO
import kr.co.testcode.repository.UserRepository
import kr.co.testcode.repository.entity.UserEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val mailSender: MailSender
) {

    fun getById(id: Long): UserEntity? {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
    }

    fun getByEmail(email: String): UserEntity {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE) ?: throw ResourceNotFoundException("Users", email)
    }

    fun getByIdOrElseThrow(id: Long): UserEntity {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE) ?: throw ResourceNotFoundException("Users", id)
    }

    @Transactional
    fun createUser(userCreateDto: UserCreateDTO): UserEntity {
        val userEntity = UserEntity(
            email = userCreateDto.email,
            nickname = userCreateDto.nickname,
            address = userCreateDto.address,
            status = UserStatus.PENDING,
            certificationCode = UUID.randomUUID().toString()
        )

        userRepository.save(userEntity).run {
            val certificationUrl: String = generateCertificationUrl(userEntity)
            sendCertificationEmail(userCreateDto.email, certificationUrl)
        }

        return userEntity
    }

    @Transactional
    fun updateUser(id: Long, userUpdateDto: UserUpdateDTO): UserEntity {
        val userEntity = getByIdOrElseThrow(id)
        userEntity.nickname = userUpdateDto.nickname
        userEntity.address = userUpdateDto.address
        return userEntity
    }

    @Transactional
    fun login(id: Long) {
        val userEntity = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Users", id)
        userEntity.lastLoginAt = Clock.systemUTC().millis()
    }

    @Transactional
    fun verifyEmail(id: Long, certificationCode: String) {
        val userEntity = userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Users", id)
        if (certificationCode != userEntity.certificationCode) {
            throw CertificationCodeNotMatchedException()
        }
        userEntity.status = UserStatus.ACTIVE
    }

    private fun generateCertificationUrl(userEntity: UserEntity): String {
        return ("http://localhost:8080/api/users/" + userEntity.id) + "/verify?certificationCode=" + userEntity.certificationCode
    }

    private fun sendCertificationEmail(email: String, certificationUrl: String) {
        val message = SimpleMailMessage()
        message.setTo(email)
        message.setSubject("Please certify your email address")
        message.setText("Please click the following link to certify your email address: $certificationUrl")
        mailSender.send(message)
    }
}