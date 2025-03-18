package com.example.fakerdataclass.generators

import kotlin.random.Random
import kotlin.reflect.KType

/**
 * Provee generadores para tipos de colecciones.
 */
internal object CollectionGenerators {

    /**
     * Genera una lista aleatoria de valores.
     *
     * @param itemType Tipo de los elementos de la lista
     * @param generator Función generadora de elementos
     * @return Una lista de elementos generados
     */
    fun <T> generateList(itemType: KType, generator: (KType, String?) -> Any?): List<T> {
        val size = Random.nextInt(1, 5)
        return List(size) { generator(itemType, null) as T }
    }

    /**
     * Genera un set aleatorio de valores.
     *
     * @param itemType Tipo de los elementos del set
     * @param generator Función generadora de elementos
     * @return Un set de elementos generados
     */
    fun <T> generateSet(itemType: KType, generator: (KType, String?) -> Any?): Set<T> {
        return generateList<T>(itemType, generator).toSet()
    }

    /**
     * Genera un mapa aleatorio de valores.
     *
     * @param keyType Tipo de las claves del mapa
     * @param valueType Tipo de los valores del mapa
     * @param generator Función generadora de elementos
     * @return Un mapa con claves y valores generados
     */
    fun <K, V> generateMap(
        keyType: KType,
        valueType: KType,
        generator: (KType, String?) -> Any?
    ): Map<K, V> {
        val size = Random.nextInt(1, 5)
        return (0 until size).associate {
            generator(keyType, null) as K to generator(valueType, null) as V
        }
    }
}