package com.example.fakerdataclass.generators

import kotlin.random.Random

/**
 * Provee generadores para cadenas de texto.
 */
internal object StringGenerators {

    private val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    /**
     * Genera una cadena de texto aleatoria.
     *
     * @param length Longitud de la cadena
     * @return Una cadena aleatoria
     */
    fun randomString(length: Int): String {
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    /**
     * Genera una cadena de texto contextualizada al nombre del campo.
     *
     * @param fieldName Nombre del campo
     * @return Una cadena generada según el contexto
     */
    fun generateContextualString(fieldName: String?): String {
        return when {
            fieldName == null -> randomString(8)

            // Campos de nombre
            fieldName.contains("name", ignoreCase = true) && fieldName.contains("first", ignoreCase = true) ->
                listOf("Juan", "Ana", "Carlos", "María", "Pedro", "Laura", "Miguel", "Sofía").random()
            fieldName.contains("name", ignoreCase = true) && fieldName.contains("last", ignoreCase = true) ->
                listOf("García", "Rodríguez", "López", "Martínez", "González", "Pérez", "Sánchez").random()
            fieldName.contains("name", ignoreCase = true) && !fieldName.contains("first", ignoreCase = true) && !fieldName.contains("last", ignoreCase = true) ->
                listOf("Juan García", "Ana López", "Carlos Rodríguez", "María Martínez", "Pedro González").random()

            // Campos de contacto
            fieldName.contains("email", ignoreCase = true) ->
                listOf("user@example.com", "test@domain.org", "contact@company.net", "info@service.com").random()
            fieldName.contains("phone", ignoreCase = true) ->
                "+${Random.nextInt(1, 99)}-${Random.nextInt(100, 999)}-${Random.nextInt(1000, 9999)}"

            // Campos de ubicación
            fieldName.contains("address", ignoreCase = true) ->
                listOf("Calle Principal 123", "Avenida Central 456", "Plaza Mayor 789", "Boulevard 123").random()
            fieldName.contains("country", ignoreCase = true) ->
                listOf("España", "México", "Argentina", "Colombia", "Chile", "Perú", "Ecuador").random()
            fieldName.contains("city", ignoreCase = true) ->
                listOf("Madrid", "Barcelona", "Valencia", "Sevilla", "Bilbao", "México DF", "Buenos Aires").random()

            // Campos descriptivos
            fieldName.contains("color", ignoreCase = true) ->
                listOf("Rojo", "Verde", "Azul", "Amarillo", "Negro", "Blanco", "Morado").random()
            fieldName.contains("url", ignoreCase = true) ->
                "https://www.example.com/${randomString(6)}"
            fieldName.contains("description", ignoreCase = true) ->
                "Descripción de ejemplo para pruebas automatizadas"
            fieldName.contains("title", ignoreCase = true) ->
                listOf("Título de ejemplo", "Proyecto de prueba", "Elemento de muestra", "Item de test").random()
            fieldName.contains("password", ignoreCase = true) ->
                "P@ss${Random.nextInt(1000, 9999)}"

            // Datos no reconocidos
            else -> randomString(10)
        }
    }
}