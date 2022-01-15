package com.sistemalima.dsmovie.user.model

import com.sistemalima.dsmovie.score.request.ScoreDTORequest
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_user")
class User(

    @field:Id
    @field:GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    var email: String
){
    fun toModel(request: ScoreDTORequest): User {
        return User(
            email = request.email
        )
    }
}
