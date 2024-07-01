package kr.co.testcode.controller

import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.testcode.model.dto.PostResponse
import kr.co.testcode.model.dto.PostUpdateDTO
import kr.co.testcode.repository.entity.PostEntity
import kr.co.testcode.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
@Tag(name = "포스트")
class PostController(
    private val postService: PostService,
    private val userController: UserController
) {

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(postService.getPostById(id)))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody postUpdateDto: PostUpdateDTO): ResponseEntity<PostResponse> {
        return ResponseEntity
            .ok()
            .body(toResponse(postService.updatePost(id, postUpdateDto)))
    }
    fun toResponse(postEntity: PostEntity): PostResponse {
        return PostResponse(
            id = postEntity.id!!,
            content = postEntity.content!!,
            createdAt = postEntity.createdAt!!,
            modifiedAt = postEntity.modifiedAt!!,
            writer = userController.toResponse(postEntity.writer!!)
        )
    }
}