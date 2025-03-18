package com.example.fakerdataclass.internal

import com.example.fakerdataclass.generators.CollectionGenerators
import com.example.fakerdataclass.generators.DateTimeGenerators
import com.example.fakerdataclass.generators.PrimitiveGenerators
import com.example.fakerdataclass.generators.StringGenerators
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.primaryConstructor

/**
 * Genera una instancia de una data class utilizando reflexión.
 *
 * @param clazz Clase a instanciar
 * @param customValues Valores personalizados para campos específicos
 * @return Instancia de la clase con valores generados
 */
@Suppress("UNCHECKED_CAST")
internal fun <T : Any> generateFakeData(clazz: KClass<T>, customValues: Map<String, Any?> = emptyMap()): T {
    // Verificar si es una data class
    if (!clazz.isData) {
        throw IllegalArgumentException("La clase ${clazz.simpleName} no es una data class")
    }

    // Obtener el constructor primario
    val constructor = clazz.primaryConstructor
        ?: throw IllegalArgumentException("La data class ${clazz.simpleName} no tiene un constructor primario")

    // Generar valores para cada parámetro del constructor
    val parameters = constructor.parameters
    val arguments = parameters.associateWith { parameter ->
        val paramName = parameter.name ?: return@associateWith null

        // Verificar si hay un valor personalizado para este parámetro
        if (customValues.containsKey(paramName)) {
            return@associateWith customValues[paramName]
        }

        // Generar un valor aleatorio basado en el tipo
        generateValueForType(parameter.type, paramName)
    }

    // Crear la instancia usando los argumentos generados
    return constructor.callBy(arguments)
}

/**
 * Genera un valor aleatorio para un tipo específico.
 *
 * @param type Tipo para el cual generar un valor
 * @param fieldName Nombre del campo (para contexto)
 * @return Valor generado apropiado para el tipo
 */
internal fun generateValueForType(type: KType, fieldName: String? = null): Any? {
    val classifier = type.classifier as? KClass<*> ?: return null

    // Manejar nulabilidad
    if (type.isMarkedNullable && Random.nextInt(10) < 2) {
        return null
    }

    return when {
        // Tipos primitivos
        classifier == String::class -> StringGenerators.generateContextualString(fieldName)
        classifier == Int::class -> PrimitiveGenerators.generateInt(fieldName)
        classifier == Long::class -> PrimitiveGenerators.generateLong(fieldName)
        classifier == Double::class -> PrimitiveGenerators.generateDouble(fieldName)
        classifier == Float::class -> PrimitiveGenerators.generateFloat(fieldName)
        classifier == Boolean::class -> PrimitiveGenerators.generateBoolean(fieldName)

        // Fechas y horas
        classifier == LocalDate::class -> DateTimeGenerators.generateLocalDate(fieldName)
        classifier == LocalDateTime::class -> DateTimeGenerators.generateLocalDateTime(fieldName)
        classifier == Date::class -> DateTimeGenerators.generateDate(fieldName)

        // Colecciones
        classifier == List::class -> {
            val itemType = type.arguments.firstOrNull()?.type ?: return emptyList<Any>()
            CollectionGenerators.generateList<Any>(itemType, ::generateValueForType)
        }
        classifier == Set::class -> {
            val itemType = type.arguments.firstOrNull()?.type ?: return emptySet<Any>()
            CollectionGenerators.generateSet<Any>(itemType, ::generateValueForType)
        }
        classifier == Map::class -> {
            val keyType = type.arguments.getOrNull(0)?.type ?: return emptyMap<Any, Any>()
            val valueType = type.arguments.getOrNull(1)?.type ?: return emptyMap<Any, Any>()
            CollectionGenerators.generateMap<Any, Any>(keyType, valueType, ::generateValueForType)
        }

        // Manejo de Enums
        classifier.java.isEnum -> {
            val enumConstants = classifier.java.enumConstants
            if (enumConstants.isNotEmpty()) {
                enumConstants[Random.nextInt(enumConstants.size)]
            } else {
                null
            }
        }

        // Recursión para data classes anidadas
        classifier.isData -> {
            generateFakeData(classifier)
        }

        else -> null
    }
}