package kr.co.testcode.repository.entity

import jakarta.persistence.*
import kr.co.testcode.model.UserStatus
import org.apache.catalina.User
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    var email: String? = null,

    @Column(name = "nickname")
    var nickname: String? = null,

    @Column(name = "address")
    var address: String? = null,

    @Column(name = "certification_code")
    var certificationCode: String? = null,

    @Column(name = "last_login_at")
    var lastLoginAt: Long? = null,

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: UserStatus? = UserStatus.PENDING
) {
}