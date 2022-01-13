package com.sistemalima.dsmovie.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_movie")
class Movie(

    @field:Id
    @field:GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    val title: String,

    val score: Double,

    val count: Int,

    val image: String
)
