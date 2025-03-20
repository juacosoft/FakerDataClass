package com.mtzdev.fakerdataclass.generators

import kotlin.random.Random

/**
 * Provee generadores para cadenas de texto.
 */
internal object StringGenerators {

    private val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private val suggestedFirstNames = listOf(
        "Juan",
        "Ana",
        "Carlos",
        "María",
        "Pedro",
        "Laura",
        "Miguel",
        "Sofía",
        "Homer",
        "Marge",
        "Lisa",
        "Bart",
        "Maggie",
        "Moe",
        "Carl"
    )

    private val suggestedLastNames = listOf(
        "García",
        "Rodríguez",
        "López",
        "Martínez",
        "González",
        "Pérez",
        "Sánchez",
        "Simpsom",
        "Szyslak",
        "Carlson"
    )

    private val suggestedCompleteName = listOf(
        "Homer Simpson",
        "Marge Simpson",
        "Lisa Simpson",
        "Bart Simpson",
        "Maggie Simpson",
        "Moe Szyslak",
        "Carl Carlson"
    )

    private val suggestedEmails = listOf(
        "user@example.com", "test@domain.org", "contact@company.net", "info@service.com"
    )

    private val suggestedAddresses = listOf(
        "742 Evergreen Terrace",
        "39 East District, Mount Paozu, Earth",
        "Mystery Machine, Various Locations, USA",
        "124 Conch Street, Bikini Bottom, Pacific Ocean"
    )

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
                suggestedFirstNames.random()
            fieldName.contains("name", ignoreCase = true) && fieldName.contains("last", ignoreCase = true) ->
                suggestedLastNames.random()
            fieldName.contains("name", ignoreCase = true) && !fieldName.contains("first", ignoreCase = true) && !fieldName.contains("last", ignoreCase = true) ->
                suggestedCompleteName.random()

            // Campos de contacto
            fieldName.contains("email", ignoreCase = true) ->
                suggestedEmails.random()
            fieldName.contains("phone", ignoreCase = true) ->
                "+${Random.nextInt(1, 99)}-${Random.nextInt(100, 999)}-${Random.nextInt(1000, 9999)}"

            // Campos de ubicación
            fieldName.contains("address", ignoreCase = true) ->
                suggestedAddresses.random()
            fieldName.contains("country", ignoreCase = true) ->
                listOf("España", "México", "Argentina", "Colombia", "Chile", "Perú", "Ecuador").random()
            fieldName.contains("city", ignoreCase = true) ->
                listOf("Madrid", "Barcelona", "Valencia", "Sevilla", "Bilbao", "México DF", "Buenos Aires").random()

            // Campos descriptivos
            fieldName.contains("color", ignoreCase = true) ->
                listOf("Rojo", "Verde", "Azul", "Amarillo", "Negro", "Blanco", "Morado").random()

            fieldName.contains("url", ignoreCase = true)
                    || fieldName.contains("web", ignoreCase = true)
                    || fieldName.contains("link", ignoreCase = true)  ->
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