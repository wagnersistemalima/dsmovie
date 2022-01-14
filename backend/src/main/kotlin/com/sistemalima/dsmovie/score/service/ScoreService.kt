package com.sistemalima.dsmovie.score.service

import com.sistemalima.dsmovie.advice.exceptions.ScoreException
import com.sistemalima.dsmovie.constant.ProcessingResult
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.movie.repository.MovieRepository
import com.sistemalima.dsmovie.score.model.Score
import com.sistemalima.dsmovie.score.repository.ScoreRepository
import com.sistemalima.dsmovie.score.request.ScoreDTORequest
import com.sistemalima.dsmovie.user.model.User
import com.sistemalima.dsmovie.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScoreService(
    @field:Autowired val movieRepository: MovieRepository,
    @field:Autowired val userRepository: UserRepository,
    @field:Autowired val scoreRepository: ScoreRepository
) {

    val logger = LoggerFactory.getLogger(ScoreService::class.java)

    @Transactional
    fun saveScore(request: ScoreDTORequest, correlationId: String): MovieDTOResponse {

        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice: Dsmovie -> " +
                    "class: ScoreService -> method: saveScore() -> " +
                    "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )

        val response = salvarNovaAvaliacaoDeUmNovousuario(request)

        logger.info(
            "${ProcessingResult.GET_MOVIMENT_NUMBER} -> microservice: Dsmovie -> " +
                    "class: ScoreService -> method: saveScore() -> new Score save SUCCESS -> " +
                    "${ProcessingResult.GET_MOVIMENT_NUMBER.message} -> correlationId: $correlationId"
        )
        return response
    }

    fun salvarNovaAvaliacaoDeUmNovousuario(request: ScoreDTORequest): MovieDTOResponse {
        if (!userRepository.existsByEmail(request.email)) {
            val user = User(
                email = request.email
            )
            userRepository.save(user)

            val movie = movieRepository.findById(request.movieId).get()
            val score = Score(
                value = request.score
            )
            score.setMovie(movie)
            score.setUser(user)
            score.value = request.score

            scoreRepository.save(score)

            movie.scores.add(score)
            var sum = 0.0
            for (s: Score in movie.scores) {
                sum += s.value
            }

            val avg = sum / movie.scores.size
            movie.score = avg
            movie.count = movie.scores.size

            movieRepository.save(movie)
            return MovieDTOResponse(movie)
        }
        logger.error("Error: microservice: Dsmovie -> class: ScoreService ->" +
                " method: salvarNovaAvaliacaoDeUmNovousuario -> request: $request -> " +
                "${ProcessingResult.SCORE_EXCEPTION} -> ${ProcessingResult.SCORE_EXCEPTION.message}")
        throw ScoreException("Estamos trabalhando no erro")
    }
}
