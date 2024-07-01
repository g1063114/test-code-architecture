package kr.co.testcode.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.testcode.model.dto.UserCreateDTO
import kr.co.testcode.model.dto.UserResponse
import kr.co.testcode.repository.entity.UserEntity
import kr.co.testcode.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
@Tag(name = "유저")
class UserCreateController(
    private val userController: UserController,
    private val userService: UserService
) {

    @PostMapping
    fun createUser(@RequestBody userCreateDto: UserCreateDTO): ResponseEntity<UserResponse> {
        val userEntity: UserEntity = userService.createUser(userCreateDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(userController.toResponse(userEntity))
    }
}