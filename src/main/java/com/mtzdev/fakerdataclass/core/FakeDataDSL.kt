package com.mtzdev.fakerdataclass.core

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Constructor de datos falsos con DSL fluido basado en referencias a propiedades.
 * Permite personalizar propiedades específicas de manera type-safe.
 */
class FakeDataDSL<T : Any>(private val clazz: KClass<T>) {
    private val customValues = mutableMapOf<String, Any?>()

    /**
     * Referencia a una propiedad para configurar su valor fake.
     *
     * @param property Referencia a la propiedad (usando ::propiedad)
     * @return PropertyConfigurator para la propiedad
     */
    fun <R> property(property: KProperty1<T, R>): PropertyConfigurator<R> {
        return PropertyConfigurator(property.name, customValues)
    }

    class PropertyConfigurator<R>(
        private val propertyName: String,
        private val customValues: MutableMap<String, Any?>
    ) {
        /**
         * Establece el valor que debe retornar la propiedad.
         *
         * @param value El valor a asignar
         */
        fun returns(value: R) {
            customValues[propertyName] = value
        }

        /**
         * Establece que la propiedad debe ser nula.
         */
        fun returnsNull() {
            customValues[propertyName] = null
        }
    }

    fun build(): Map<String, Any?> = customValues.toMap()
}