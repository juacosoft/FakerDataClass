package com.mtzdev.fakerdataclass.core

import com.mtzdev.fakerdataclass.internal.FakeDataFactory

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

/**
 * Crea una instancia falsa usando un DSL fluido con referencias a propiedades.
 *
 * @param T El tipo de data class a generar
 * @param block Bloque de configuración DSL
 * @return Instancia generada con las propiedades configuradas
 */
inline fun <reified T : Any> fakeData(block: FakeDataDSL<T>.() -> Unit): T {
    val dsl = FakeDataDSL(T::class)
    dsl.block()
    return FakeDataFactory.create(T::class, dsl.build())
}