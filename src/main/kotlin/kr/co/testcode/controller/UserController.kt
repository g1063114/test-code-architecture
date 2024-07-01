package kr.co.testcode.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.testcode.model.dto.MyProfileResponse
import kr.co.testcode.model.dto.UserResponse
import kr.co.testcode.model.dto.UserUpdateDTO
import kr.co.testcode.repository.entity.UserEntity
import kr.co.testcode.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/users")
@Tag(name = "유저")
class UserController(
    private val userService: UserService
) {
    @ResponseStatus
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(userService.getByIdOrElseThrow(id)))
    }

    @GetMapping("/{id}/verify")
    fun verifyEmail(
        @PathVariable id: Long,
        @RequestParam certificationCode: String?
    ): ResponseEntity<Void> {
        userService.verifyEmail(id, certificationCode!!)
        return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create("http://localhost:3000"))
            .build()
    }

    @GetMapping("/me")
    fun getMyInfo(
        @RequestHeader("EMAIL") email: String?
    ): ResponseEntity<MyProfileResponse> {
        val userEntity = userService.getByEmail(email!!)
        userService.login(userEntity.id!!)
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(userEntity))
    }

    @PutMapping("/me")
    fun updateMyInfo(
        @RequestHeader("EMAIL") email: String?,
        @RequestBody userUpdateDto: UserUpdateDTO
    ): ResponseEntity<MyProfileResponse> {
        var userEntity = userService.getByEmail(email!!)
        userEntity = userService.updateUser(userEntity.id!!, userUpdateDto)
        return ResponseEntity
            .ok()
            .body(toMyProfileResponse(userEntity))
    }

    fun toResponse(userEntity: UserEntity): UserResponse {
        return UserResponse(
            id = userEntity.id!!,
            email = userEntity.email!!,
            nickname = userEntity.nickname!!,
            status = userEntity.status!!,
            lastLoginAt = userEntity.lastLoginAt
        )
    }

    fun toMyProfileResponse(userEntity: UserEntity): MyProfileResponse {
        return MyProfileResponse(
            id = userEntity.id!!,
            email = userEntity.email!!,
            nickname = userEntity.nickname!!,
            address = userEntity.address!!,
            status = userEntity.status!!,
            lastLoginAt = userEntity.lastLoginAt!!
        )
    }
}