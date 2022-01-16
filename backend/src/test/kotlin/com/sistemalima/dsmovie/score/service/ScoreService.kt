package com.sistemalima.dsmovie.score.service

import com.sistemalima.dsmovie.movie.model.Movie
import com.sistemalima.dsmovie.movie.repository.MovieRepository
import com.sistemalima.dsmovie.score.model.Score
import com.sistemalima.dsmovie.score.model.ScorePK
import com.sistemalima.dsmovie.score.repository.ScoreRepository
import com.sistemalima.dsmovie.score.request.ScoreDTORequest
import com.sistemalima.dsmovie.user.model.User
import com.sistemalima.dsmovie.user.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional
import java.util.UUID

@ExtendWith(SpringExtension::class)
class ScoreServiceTest {

    @field:InjectMocks
    lateinit var scoreService: ScoreService

    @field:Mock
    lateinit var movieRepository: MovieRepository

    @field:Mock
    lateinit var userRepository: UserRepository

    @field:Mock
    lateinit var scoreRepository: ScoreRepository

    private val movie = Movie(
        id = 1L,
        title = "Rambo",
        score = 6.0,
        count = 1,
        image = "img"
    )

    private val usuarioAntigo = User(
        id = 3L,
        email = "usuario@gmail.com"
    )

    private val scoreAntigo = Score(
        id = ScorePK(
            movie = movie,
            user = usuarioAntigo
        ),
        value = 2.0
    )


    @Test
    fun`must save a new evaluation of a movie, when the user does not exist in the database`() {
        // cenario
        val correlationId = UUID.randomUUID().toString()
        movie.scores.add(scoreAntigo)
        val idMovieValid = 1L

        val request = ScoreDTORequest(
            movieId = 1L,
            email = "teste@gmail.com",
            score = 5.0
        )

        val scoreNovo = Score(
            value = request.score
        )
        // comportamento
        Mockito.`when`(userRepository.existsByEmail(request.email)).thenReturn(false)
        Mockito.`when`(movieRepository.findById(idMovieValid)).thenReturn(Optional.of(movie))

        // comoportamento
        Mockito.`when`(movieRepository.save(movie)).thenReturn(movie)

        // comportamento
        Mockito.`when`(scoreRepository.save(scoreNovo)).thenReturn(scoreNovo)

        // ação
        scoreService.salvaAvaliacaoUsuario(request, correlationId)

        // assertivas
        Assertions.assertEquals(2, movie.count)
        Assertions.assertEquals(3.5, movie.score)
        Assertions.assertDoesNotThrow { scoreService.saveScore(request, correlationId) }

    }
}
