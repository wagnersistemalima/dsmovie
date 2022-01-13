package com.sistemalima.dsmovie.entity

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tb_score")
class Score(

    @field:EmbeddedId
    var id: ScorePK? = null,
    val value: Double
) {
    fun setMovie(movie: Movie) {
        id?.movie = movie
    }

    fun setUser(user: User) {
        id?.user = user
    }
}
