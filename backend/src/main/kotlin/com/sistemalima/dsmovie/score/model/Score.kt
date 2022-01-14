package com.sistemalima.dsmovie.score.model

import com.sistemalima.dsmovie.movie.model.Movie
import com.sistemalima.dsmovie.user.model.User
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tb_score")
class Score(

    @field:EmbeddedId
    var id: ScorePK? = ScorePK(
        movie = null,
        user = null
    ),

    var value: Double
) {
    fun setMovie(movie: Movie) {
        id?.movie = movie
    }

    fun setUser(user: User) {
        id?.user = user
    }
}
