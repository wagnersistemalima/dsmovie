package com.sistemalima.dsmovie.score.repository

import com.sistemalima.dsmovie.score.model.Score
import com.sistemalima.dsmovie.score.model.ScorePK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScoreRepository : JpaRepository<Score, ScorePK>
