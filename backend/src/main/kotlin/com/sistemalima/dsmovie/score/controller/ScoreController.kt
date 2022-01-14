package com.sistemalima.dsmovie.score.controller

import com.sistemalima.dsmovie.constant.ApiConstant
import com.sistemalima.dsmovie.constant.ProcessingResult
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.score.request.ScoreDTORequest
import com.sistemalima.dsmovie.score.service.ScoreService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid

@Validated
@RestController
@RequestMapping(ApiConstant.SCORE_POST)
class ScoreController(
    @field:Autowired val scoreService: ScoreService
) {

    val logger = LoggerFactory.getLogger(ScoreController::class.java)

    @PutMapping // salvar de forma idepotente, apenas uma vez
    fun saveScore(@Valid @RequestBody request: ScoreDTORequest): ResponseEntity<MovieDTOResponse> {
        val correlationId = UUID.randomUUID().toString()
        logger.info(
            "${ProcessingResult.START_PROCESS} -> " +
                "microservice: Dsmovie -> class: -> ScoreController -> method: insert() -> " +
                "request: $request -> " +
                "${ProcessingResult.START_PROCESS.message} -> correlationId: $correlationId"
        )

        val response = scoreService.saveScore(request, correlationId)

        logger.info(
            "${ProcessingResult.END_PROCESS} -> " +
                    "microservice: Dsmovie -> class: -> ScoreController -> method: insert() -> " +
                    "request: $request -> " +
                    "${ProcessingResult.END_PROCESS.message} -> correlationId: $correlationId"
        )
        return ResponseEntity.ok().body(response)
    }
}
