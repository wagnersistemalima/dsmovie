package com.sistemalima.dsmovie.movie.dtoResponse

import com.sistemalima.dsmovie.movie.model.Movie

data class MovieDTOResponse(
    val id: Long?,

    val title: String,

    val score: Double,

    val count: Int,

    val image: String
){
    constructor(movie: Movie): this(movie.id, movie.title, movie.score, movie.count, movie.image )
}
