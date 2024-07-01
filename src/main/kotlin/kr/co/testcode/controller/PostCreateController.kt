package kr.co.testcode.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.testcode.model.dto.PostCreateDTO
import kr.co.testcode.model.dto.PostResponse
import kr.co.testcode.service.PostService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
@Tag(name = "포스트")
class PostCreateController(
    private val postService: PostService,
    private val postController: PostController
) {

    @PostMapping
    fun createPost(@RequestBody postCreateDto: PostCreateDTO): ResponseEntity<PostResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(postController.toResponse(postService.createPost(postCreateDto)))
    }
}