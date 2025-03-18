package com.example.fakerdataclass.generators

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import kotlin.random.Random

/**
 * Provee generadores para tipos de fecha y hora.
 */
internal object DateTimeGenerators {

    /**
     * Genera una fecha LocalDate aleatoria.
     */
    fun generateLocalDate(fieldName: String? = null): LocalDate {
        val baseDate = LocalDate.now()

        return when {
            fieldName?.contains("birth", ignoreCase = true) == true ->
                baseDate.minusYears(Random.nextLong(18, 80))
            fieldName?.contains("expir", ignoreCase = true) == true ->
                baseDate.plusYears(Random.nextLong(1, 10))
            fieldName?.contains("start", ignoreCase = true) == true ->
                baseDate.minusDays(Random.nextLong(1, 30))
            fieldName?.contains("end", ignoreCase = true) == true ->
                baseDate.plusDays(Random.nextLong(1, 30))
            else ->
                baseDate.plusDays(Random.nextLong(-365, 365))
        }
    }

    /**
     * Genera una fecha y hora LocalDateTime aleatoria.
     */
    fun generateLocalDateTime(fieldName: String? = null): LocalDateTime {
        val baseDateTime = LocalDateTime.now()

        return when {
            fieldName?.contains("created", ignoreCase = true) == true ->
                baseDateTime.minusDays(Random.nextLong(1, 30))
            fieldName?.contains("updated", ignoreCase = true) == true ->
                baseDateTime.minusHours(Random.nextLong(1, 24))
            fieldName?.contains("scheduled", ignoreCase = true) == true ->
                baseDateTime.plusDays(Random.nextLong(1, 30))
            else ->
                baseDateTime.plusHours(Random.nextLong(-24, 24))
        }
    }

    /**
     * Genera un objeto Date de Java aleatorio.
     */
    fun generateDate(fieldName: String? = null): Date {
        val now = System.currentTimeMillis()
        val offset = when {
            fieldName?.contains("created", ignoreCase = true) == true ->
                -Random.nextLong(1, 30) * 86400000 // días en milisegundos
            fieldName?.contains("updated", ignoreCase = true) == true ->
                -Random.nextLong(1, 24) * 3600000  // horas en milisegundos
            else ->
                Random.nextLong(-30, 30) * 86400000
        }

        return Date(now + offset)
    }
}