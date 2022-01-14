package com.sistemalima.dsmovie.advice

import java.time.Instant

data class ValidationError(
    val timestamp: Instant,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
) {
    val errors = mutableListOf<FieldMessage>()

    fun addError(fieldName: String, message: String) {
        errors.add(FieldMessage(fieldName, message))
    }
}
