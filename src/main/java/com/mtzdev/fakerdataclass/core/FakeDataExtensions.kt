package com.mtzdev.fakerdataclass.core

/**
 * Genera una lista de instancias falsas de una data class.
 *
 * @param T Tipo de la data class
 * @param size Tamaño de la lista a generar
 * @return Lista de instancias falsas
 */
inline fun <reified T : Any> fakeList(size: Int): List<T> {
    return List(size) { fakeData<T>() }
}

/**
 * Genera un conjunto de instancias falsas de una data class.
 *
 * @param T Tipo de la data class
 * @param size Tamaño del conjunto a generar
 * @return Conjunto de instancias falsas
 */
inline fun <reified T : Any> fakeSet(size: Int): Set<T> {
    return fakeList<T>(size).toSet()
}