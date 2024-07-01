package kr.co.testcode.repository.entity

import jakarta.persistence.*

@Entity
@Table(name = "posts")
class PostEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "content")
    var content: String? = null,

    @Column(name = "created_at")
    val createdAt: Long? = null,

    @Column(name = "modified_at")
    var modifiedAt: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val writer: UserEntity? = null
) {
}