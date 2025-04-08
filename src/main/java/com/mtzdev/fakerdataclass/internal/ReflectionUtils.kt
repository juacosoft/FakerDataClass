package com.mtzdev.fakerdataclass.internal

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mtzdev.fakerdataclass.generators.CollectionGenerators
import com.mtzdev.fakerdataclass.generators.DateTimeGenerators
import com.mtzdev.fakerdataclass.generators.PrimitiveGenerators
import com.mtzdev.fakerdataclass.generators.ResourceGenerator
import com.mtzdev.fakerdataclass.generators.StringGenerators
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
    if (!clazz.isData) {
        throw IllegalArgumentException("La clase ${clazz.simpleName} no es una data class")
    }

    val constructor = clazz.primaryConstructor
        ?: throw IllegalArgumentException("La data class ${clazz.simpleName} no tiene un constructor primario")

    val parameters = constructor.parameters
    val arguments = parameters.associateWith { parameter ->
        val paramName = parameter.name ?: return@associateWith null

        if (customValues.containsKey(paramName)) {
            return@associateWith customValues[paramName]
        }
        val annotations = parameter.annotations
        generateValueForType(parameter.type, paramName, annotations)
    }

    return constructor.callBy(arguments)
}

/**
 * Genera un valor aleatorio para un tipo específico.
 *
 * @param type Tipo para el cual generar un valor
 * @param fieldName Nombre del campo (para contexto)
 * @return Valor generado apropiado para el tipo
 */
internal fun generateValueForType(
    type: KType,
    fieldName: String? = null,
    annotations: List<Annotation> = emptyList()
): Any? {
    // Check for specific annotations

    val classifier = type.classifier as? KClass<*> ?: return null

    if (type.isMarkedNullable && Random.nextInt(10) < 2) {
        return null
    }
    val hasDrawableResAnnotation = annotations.any { it is DrawableRes }
    val hasStringResAnnotation = annotations.any { it is StringRes }

    return when {
        // special types
        classifier == Uri::class -> {
            Uri.parse("https://fakeurl.com")
        }
        classifier == Bundle::class -> {
            Bundle().apply {
                putString("stringkey", "some value")
            }
        }
        classifier == Drawable::class ->
            ResourceGenerator.generateResource(ResourceGenerator.ResourceType.DRAWABLE)

        classifier == Color::class ->
            ResourceGenerator.generateResource(ResourceGenerator.ResourceType.COLOR)

        classifier == Int::class && hasDrawableResAnnotation ->
            ResourceGenerator.generateResource(ResourceGenerator.ResourceType.DRAWABLE)

        classifier == Int::class && hasStringResAnnotation ->
            ResourceGenerator.generateResource(ResourceGenerator.ResourceType.STRING)

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