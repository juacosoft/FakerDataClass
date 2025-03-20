package com.mtzdev.fakerdataclass.core

import com.mtzdev.fakerdataclass.internal.FakeDataFactory

/**
 * Builder para construir objetos falsos de manera declarativa usando DSL.
 */
class FakeDataBuilder<T : Any> {
    private val customValues = mutableMapOf<String, Any?>()

    /**
     * Define un campo personalizado con un valor específico.
     *
     * @param name Nombre del campo
     * @param value Valor a asignar
     */
    fun field(name: String, value: Any?) {
        customValues[name] = value
    }

    fun build(): Map<String, Any?> = customValues.toMap()
}

/**
 * Crea una instancia falsa usando un DSL para especificar campos personalizados.
 *
 * @param T El tipo de la data class a generar
 * @param block Bloque de configuración DSL
 * @return Una instancia de T con valores generados y personalizados
 */
inline fun <reified T : Any> fake(block: FakeDataBuilder<T>.() -> Unit): T {
    val builder = FakeDataBuilder<T>()
    builder.block()
    return FakeDataFactory.create(T::class, builder.build())
}