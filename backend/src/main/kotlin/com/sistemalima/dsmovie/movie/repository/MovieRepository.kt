package com.sistemalima.dsmovie.movie.repository

import com.sistemalima.dsmovie.movie.model.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, Long>
