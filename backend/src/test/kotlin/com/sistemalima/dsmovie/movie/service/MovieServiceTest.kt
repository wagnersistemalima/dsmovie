package com.sistemalima.dsmovie.movie.service

import com.sistemalima.dsmovie.advice.exceptions.ResourceNotFoundException
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.movie.model.Movie
import com.sistemalima.dsmovie.movie.repository.MovieRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional
import java.util.UUID

@ExtendWith(SpringExtension::class)
class MovieServiceTest {

    @field:InjectMocks
    lateinit var movieService: MovieService

    @field:Mock
    lateinit var movieRepository: MovieRepository

    @Test
    fun `should return a paginated list of movies`() {
        // cenario

        val pageRequest = factoryPageRequest()
        val movie = getFactoryMovies()

        // ação

        val list = PageImpl(listOf(movie))
        val response = list.map { MovieDTOResponse(it) }
        val correlationId = UUID.randomUUID().toString()

        // comportamento
        Mockito.`when`(movieRepository.findAll(pageRequest)).thenReturn(list)
        movieRepository.findAll(pageRequest)
        // assertivas
        Mockito.verify(movieRepository).findAll(pageRequest)
        Assertions.assertEquals(response, movieService.findAll(correlationId, pageRequest))
    }

    @Test
    fun `should return a dto of a movie, when movie id exists`() {
        // cenario
        val idMovieExist = 1L
        val movie = getFactoryMovies()
        val response = MovieDTOResponse(movie)
        // ação

        val correlationId = UUID.randomUUID().toString()

        // comportamento
        Mockito.`when`(movieRepository.findById(idMovieExist)).thenReturn(Optional.of(movie))

        // assertivas
        Assertions.assertDoesNotThrow { movieService.findById(correlationId, idMovieExist) }
        Mockito.verify(movieRepository).findById(idMovieExist)
        Assertions.assertEquals(response, movieService.findById(correlationId, idMovieExist))
    }

    @Test
    fun `should return exception entity not found, when there is no movie id`() {
        // cenario
        val idMovieNotExist = 5000L

        // ação

        val correlationId = UUID.randomUUID().toString()

        // comportamento
        Mockito.`when`(movieRepository.findById(idMovieNotExist)).thenReturn(Optional.empty())

        // assertivas
        Assertions.assertThrows(ResourceNotFoundException::class.java) { movieService.findById(correlationId, idMovieNotExist) }
        Mockito.verify(movieRepository).findById(idMovieNotExist)
    }

    private fun factoryPageRequest(): PageRequest {

        val page = 0
        val linesPerPage = 12
        val direction = "ASC"
        val orderBy = "name"

        val pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy)

        return pageRequest
    }

    private fun getFactoryMovies(): Movie {
        return Movie(
            id = 1,
            title = "Guerra mundial Z",
            score = 2.0,
            count = 1,
            image = "image"
        )
    }
}
