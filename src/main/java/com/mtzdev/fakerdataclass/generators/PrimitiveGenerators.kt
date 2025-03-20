package com.mtzdev.fakerdataclass.generators

import kotlin.random.Random

/**
 * Provee generadores para tipos primitivos.
 */
internal object PrimitiveGenerators {

    /**
     * Genera un entero aleatorio.
     */
    fun generateInt(fieldName: String? = null): Int {
        return when {
            fieldName?.contains("age", ignoreCase = true) == true -> Random.nextInt(18, 80)
            fieldName?.contains("year", ignoreCase = true) == true -> Random.nextInt(2000, 2025)
            fieldName?.contains("count", ignoreCase = true) == true -> Random.nextInt(0, 100)
            else -> Random.nextInt(-100, 1000)
        }
    }

    /**
     * Genera un valor booleano aleatorio.
     */
    fun generateBoolean(fieldName: String? = null): Boolean {
        return when {
            fieldName?.contains("active", ignoreCase = true) == true -> true
            fieldName?.contains("enabled", ignoreCase = true) == true -> true
            fieldName?.contains("deleted", ignoreCase = true) == true -> false
            else -> Random.nextBoolean()
        }
    }

    /**
     * Genera un número decimal aleatorio.
     */
    fun generateDouble(fieldName: String? = null): Double {
        return when {
            fieldName?.contains("price", ignoreCase = true) == true -> Random.nextDouble(0.99, 999.99)
            fieldName?.contains("rating", ignoreCase = true) == true -> Random.nextDouble(1.0, 5.0)
            fieldName?.contains("score", ignoreCase = true) == true -> Random.nextDouble(0.0, 100.0)
            else -> Random.nextDouble(0.0, 1000.0)
        }
    }

    /**
     * Genera un número float aleatorio.
     */
    fun generateFloat(fieldName: String? = null): Float {
        return generateDouble(fieldName).toFloat()
    }

    /**
     * Genera un número long aleatorio.
     */
    fun generateLong(fieldName: String? = null): Long {
        return when {
            fieldName?.contains("id", ignoreCase = true) == true -> Random.nextLong(1, 10000)
            fieldName?.contains("timestamp", ignoreCase = true) == true -> System.currentTimeMillis() + Random.nextLong(-86400000, 86400000)
            else -> Random.nextLong(-100, 1000)
        }
    }
}