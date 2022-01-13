package com.sistemalima.dsmovie.entity

import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Embeddable
data class ScorePK(

    @field:ManyToOne
    @field:JoinColumn(name = "movie_id")
    var movie: Movie,

    @field:ManyToOne
    @field:JoinColumn(name = "user_id")
    var user: User
) : Serializable {
    companion object {
        private const val serialVersionUID = 927823009694036541L
    }
}
