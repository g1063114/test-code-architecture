package kr.co.testcode.repository

import kr.co.testcode.repository.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<PostEntity, Long> {
}