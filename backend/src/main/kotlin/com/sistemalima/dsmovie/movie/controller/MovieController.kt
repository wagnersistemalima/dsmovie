package com.sistemalima.dsmovie.movie.controller

import com.sistemalima.dsmovie.constant.ApiConstant
import com.sistemalima.dsmovie.constant.ProcessingResult
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.movie.service.MovieService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/movies")
class MovieController(
    @field:Autowired val movieService: MovieService
) {

    val logger = LoggerFactory.getLogger(MovieController::class.java)

    @GetMapping
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0",) page: Int,
        @RequestParam(value = "linesPerPage", defaultValue = "12") linesPerPage: Int,
        @RequestParam(value = "direction", defaultValue = "ASC") direction: String,
        @RequestParam(value = "orderBy", defaultValue = "title") orderBy: String

    ): ResponseEntity<Page<MovieDTOResponse>> {
        val correlationId = UUID.randomUUID().toString()

        logger.info(
            "${ProcessingResult.START_PROCESS} -> microservice -> Dsmovie -> " +
                "class: MovieController -> method: findAll() -> " +
                "${ProcessingResult.START_PROCESS.message} -> correlationId: $correlationId"
        )

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        val response = movieService.findAll(correlationId, pageRequest)

        logger.info(
            "${ProcessingResult.END_PROCESS} -> microservice -> Dsmovie -> " +
                "class: MovieController -> method: findAll() return -> " +
                "${ProcessingResult.END_PROCESS.message} -> correlationId: $correlationId"
        )
        return ResponseEntity.ok().body(response)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: Long): ResponseEntity<MovieDTOResponse> {

        val correlationId = UUID.randomUUID().toString()

        logger.info(
            "${ProcessingResult.START_PROCESS} -> microservice -> Dsmovie -> " +
                "class: MovieController -> method: findById() -> idMovies: $id ->  " +
                "${ProcessingResult.START_PROCESS.message} -> correlationId: $correlationId"
        )

        val response = movieService.findById(correlationId, id)
        return ResponseEntity.ok().body(response)
    }
}
