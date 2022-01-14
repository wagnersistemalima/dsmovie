package com.sistemalima.dsmovie.movie.model

import com.sistemalima.dsmovie.score.model.Score
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "tb_movie")
class Movie(

    @field:Id
    @field:GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    val title: String,

    var score: Double,

    var count: Int,

    val image: String
) {
    @field:OneToMany(mappedBy = "id.movie")
    val scores = mutableSetOf<Score>()
}
