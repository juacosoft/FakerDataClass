package com.example.fakerdataclass.core

import com.example.fakerdataclass.internal.FakeDataFactory

/**
 * Genera una instancia de una data class con valores aleatorios.
 *
 * @param T El tipo de la data class a generar
 * @return Una instancia de T con valores generados aleatoriamente
 */
inline fun <reified T : Any> fakeData(): T {
    return FakeDataFactory.create(T::class)
}

/**
 * Genera una instancia de una data class con valores aleatorios,
 * permitiendo personalizar campos específicos con un mapa.
 *
 * @param T El tipo de la data class a generar
 * @param customValues Mapa de valores personalizados para ciertos campos
 * @return Una instancia de T con valores generados aleatoriamente
 */
inline fun <reified T : Any> fakeData(customValues: Map<String, Any?>): T {
    return FakeDataFactory.create(T::class, customValues)
}

/**
 * Genera una instancia de una data class con valores aleatorios,
 * permitiendo personalizar campos específicos directamente como parámetros con nombre.
 *
 * @param T El tipo de la data class a generar
 * @param params Los parámetros personalizados como pares (nombre, valor)
 * @return Una instancia de T con valores generados aleatoriamente
 */
inline fun <reified T : Any> fakeData(vararg params: Pair<String, Any?>): T {
    return FakeDataFactory.create(T::class, params.toMap())
}