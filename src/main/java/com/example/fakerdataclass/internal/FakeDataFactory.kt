package com.example.fakerdataclass.internal

import kotlin.reflect.KClass

object FakeDataFactory {

    /**
     * Crea una instancia de una data class con valores aleatorios.
     * Esta función NO es inline, por lo que puede acceder a funciones internas.
     *
     * @param clazz La clase a instanciar
     * @return Una instancia con valores aleatorios
     */
    fun <T : Any> create(clazz: KClass<T>): T {
        return generateFakeData(clazz)
    }

    /**
     * Crea una instancia de una data class con valores personalizados.
     * Esta función NO es inline, por lo que puede acceder a funciones internas.
     *
     * @param clazz La clase a instanciar
     * @param customValues Mapa de valores personalizados
     * @return Una instancia con valores aleatorios y personalizados
     */
    fun <T : Any> create(clazz: KClass<T>, customValues: Map<String, Any?>): T {
        return generateFakeData(clazz, customValues)
    }
}