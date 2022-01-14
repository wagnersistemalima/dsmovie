package com.sistemalima.dsmovie.score.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class ScoreDTORequest(

    @field:NotNull
    val movieId: Long,

    @field:Email
    val email: String,

    val score: Double

)
