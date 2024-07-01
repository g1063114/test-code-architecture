package kr.co.testcode.service

import kr.co.testcode.exception.ResourceNotFoundException
import kr.co.testcode.model.dto.PostCreateDTO
import kr.co.testcode.model.dto.PostUpdateDTO
import kr.co.testcode.repository.PostRepository
import kr.co.testcode.repository.entity.PostEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock

@Service
class PostService(
    private val postRepository: PostRepository,
    private val userService: UserService
) {

    fun getPostById(id: Long): PostEntity {
        return postRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Posts", id)
    }

    @Transactional
    fun createPost(postCreateDto: PostCreateDTO): PostEntity {
        val userEntity = userService.getByIdOrElseThrow(postCreateDto.writerId)
        val postEntity = PostEntity(
            writer = userEntity,
            content = postCreateDto.content,
            createdAt = Clock.systemUTC().millis()
        )
        return postRepository.save(postEntity)
    }

    @Transactional
    fun updatePost(id: Long, postUpdateDto: PostUpdateDTO): PostEntity {
        val postEntity = getPostById(id)
        postEntity.content = postUpdateDto.content
        postEntity.modifiedAt = Clock.systemUTC().millis()
        return postRepository.save(postEntity)
    }
}