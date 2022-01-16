package com.sistemalima.dsmovie.movie.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.sistemalima.dsmovie.advice.exceptions.ResourceNotFoundException
import com.sistemalima.dsmovie.movie.dtoResponse.MovieDTOResponse
import com.sistemalima.dsmovie.movie.model.Movie
import com.sistemalima.dsmovie.movie.service.MovieService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class MovieControllerTest {

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:MockBean
    lateinit var movieService: MovieService

    @Test
    fun `should return 200 ok with a paginated list of movies`() {
        // cenario

        val uri = URI("/movies")

        val movie = getFactoryMovies()
        val list = PageImpl(listOf(movie))
        val response = list.map { movie -> MovieDTOResponse(movie) }

        // ação

        // comportamento
        Mockito.`when`(movieService.findAll(any(), any())).thenReturn(response)

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

        // assertivas
        Mockito.verify(movieService).findAll(any(), any())
        Assertions.assertNotNull(response)
    }

    @Test
    fun `should return 200 ok, when searching the movie by id`() {
        // cenario

        val idMovieValid = 1L
        val uri = UriComponentsBuilder.fromUriString("/movies" + "/{id}").buildAndExpand(idMovieValid).toUri()

        val movie = getFactoryMovies()
        val response = MovieDTOResponse(movie)

        // ação

        // comportamento
        Mockito.`when`(movieService.findById(any(), any())).thenReturn(response)

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(toJsonDto(response)))

        // assertivas
        Mockito.verify(movieService).findById(any(), any())
        Assertions.assertNotNull(response)
    }

    @Test
    fun `should return 404 not found, when searching for the movie id that doesn't exist`() {
        // cenario

        val idMovieValid = 5000L
        val uri = UriComponentsBuilder.fromUriString("/movies" + "/{id}").buildAndExpand(idMovieValid).toUri()

        // ação

        // comportamento
        Mockito.`when`(movieService.findById(any(), any())).thenThrow(ResourceNotFoundException("Entity not found"))

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound)

        // assertivas
        Mockito.verify(movieService).findById(any(), any())
    }

    private fun toJsonDto(response: MovieDTOResponse): String {
        return objectMapper.writeValueAsString(response)
    }

    private fun toJson(response: Page<MovieDTOResponse>): String {
        return objectMapper.writeValueAsString(response)
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
