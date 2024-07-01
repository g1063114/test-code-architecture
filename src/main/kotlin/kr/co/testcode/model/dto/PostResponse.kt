package kr.co.testcode.model.dto

class PostResponse(
    var id: Long,
    val content: String,
    val createdAt: Long,
    val modifiedAt: Long,
    val writer: UserResponse,
) {
}