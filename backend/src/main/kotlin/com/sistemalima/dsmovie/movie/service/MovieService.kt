package com.sistemalima.dsmovie.movie.service

import com.sistemalima.dsmovie.advice.exceptions.ResourceNotFoundException
import com.sistemalima.dsmovie.constant.ProcessingResult
import com.sistemalima.dsmovie.movie.controller.MovieController
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.movie.repository.MovieRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MovieService(@field:Autowired val movieRepository: MovieRepository) {

    val logger = LoggerFactory.getLogger(MovieController::class.java)

    @Transactional(readOnly = true)
    fun findAll(correlationId: String, pageRequest: PageRequest): Page<MovieDTOResponse> {
        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice -> Dsmovie -> " +
                "class: MovieService -> method: findAll() -> " +
                "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )

        val list = movieRepository.findAll(pageRequest)
        val response = list.map { movie -> MovieDTOResponse(movie) }

        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice -> Dsmovie -> " +
                "class: MovieService -> method: findAll() -> return SUCCESS -> " +
                "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )
        return response
    }

    @Transactional(readOnly = true)
    fun findById(correlationId: String, id: Long): MovieDTOResponse {

        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice -> Dsmovie -> " +
                "class: MovieService -> method: findById() -> idMovie: $id ->" +
                "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )

        val movie = movieRepository.findById(id).orElseGet {
            logger.error(
                "Error: microservice: Dsmovie -> class: MovieService ->" +
                    "method: findById() -> idMovie: $id -> ${ProcessingResult.ENTITY_NOT_FOUND_EXCEPTION} -> " +
                    "${ProcessingResult.ENTITY_NOT_FOUND_EXCEPTION.message} -> " +
                    "correlationId: $correlationId"
            )
            throw ResourceNotFoundException("Error: idMovie: $id -> not found")
        }
        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice -> Dsmovie -> " +
                "class: MovieService -> method: findById() -> idMovie: $id -> return SUCCESS -> " +
                "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )
        return MovieDTOResponse(movie)
    }
}
