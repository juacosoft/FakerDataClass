# FakerDataClass

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)

Una biblioteca ligera para Kotlin/Android que genera automáticamente instancias de data classes con datos aleatorios para simplificar tus tests unitarios.

## 🌟 Características

- ✨ **Generación automática** de instancias de data classes con datos aleatorios
- 🛠️ **API flexible** con múltiples opciones de personalización
- 🧩 **Soporte para tipos anidados** - data classes dentro de data classes, enums, y colecciones
- 🔒 **Type-safe** con sintaxis concisa usando referencias a propiedades de Kotlin

## 📦 Instalación
### Gradle (settings.gradle)
```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
### Gradle (build.gradle)

```groovy
dependencies {
    implementation 'com.github.juacosoft:fakerdataclass:vLASTED_VERSION'
}
```

## 🚀 Ejemplos de Uso

### Generación Básica de Datos Aleatorios

Crea instancias completamente aleatorias de tus data classes:

```kotlin
// Generar una instancia con valores aleatorios
val fakeDto = fakeData<SimpleTestDTO>()

assertNotNull(fakeDto)
println(fakeDto) // Todos los campos tendrán valores aleatorios
```

### Personalización con Pares Clave-Valor

Especifica valores para ciertos campos mientras los demás se generan aleatoriamente:

```kotlin
// Usando pares clave-valor para personalizar campos específicos
val fakeDto = fakeData<SimpleTestDTO>(
    "name" to "John Doe",
    "age" to 30,
    "email" to "some@mail.com"
)

assert(fakeDto.name == "John Doe")
assert(fakeDto.age == 30)
assert(fakeDto.email == "some@mail.com")
```

### DSL con Referencias a Propiedades (Type-Safe)

Usa un DSL más expresivo y type-safe con referencias directas a propiedades:

```kotlin
// Usando el DSL con referencias a propiedades
val fakeDto = fakeData<SimpleTestDTO> {
    property(SimpleTestDTO::name).returns("John Doe")
    property(SimpleTestDTO::age).returns(30)
    property(SimpleTestDTO::email).returns("some@mail.com")
}

assert(fakeDto.name == "John Doe")
assert(fakeDto.age == 30)
assert(fakeDto.email == "some@mail.com")
```

### Manejo de Tipos Complejos y Anidados

FakeData maneja fácilmente data classes anidadas, enums y colecciones:

```kotlin
// Genera primero un objeto anidado
val userTestDTO = fakeData<UserTestDTO> {
    property(UserTestDTO::name).returns("John Doe")
    property(UserTestDTO::age).returns(30)
    property(UserTestDTO::email).returns("some@mail.com")
}

// Luego úsalo en el objeto principal
val fakeDto = fakeData<CompleteTestDto> {
    property(CompleteTestDto::user).returns(userTestDTO)
    property(CompleteTestDto::type).returns(UserType.ADMIN)
}

// Verificaciones
assert(fakeDto.user.name == "John Doe")
assert(fakeDto.user.age == 30)
assert(fakeDto.user.email == "some@mail.com")
assert(fakeDto.atributes.isNotEmpty()) // Lista generada automáticamente
assert(fakeDto.type == UserType.ADMIN)
```

## 🏗️ Estructura de data classes soportada

La biblioteca está diseñada para trabajar con data classes como estas:

```kotlin
// Data class simple
data class SimpleTestDTO(
    val name: String,
    val age: Int,
    val email: String
)

// Enum para usar en tests
enum class UserType {
    ADMIN, REGULAR, GUEST
}

// Data class anidada
data class UserTestDTO(
    val name: String,
    val age: Int,
    val email: String
)

// Data class compleja con tipos anidados
data class CompleteTestDto(
    val user: UserTestDTO,
    val atributes: List<String>,
    val type: UserType
)
```

## 🧪 Casos de Uso Comunes

### Tests Unitarios de ViewModels

```kotlin
@Test
fun `test ViewModel processes user data correctly`() {
    // Arrange
    val userDto = fakeData<UserTestDTO>()
    whenever(repository.getUser()).thenReturn(userDto)
    
    // Act
    viewModel.loadUser()
    
    // Assert
    verify(view).showUserDetails(eq(userDto))
}
```

### Tests de Mappers/Converters

```kotlin
@Test
fun `test entity to domain model conversion`() {
    // Arrange
    val userEntity = fakeData<UserEntity>()
    
    // Act
    val userDomain = mapper.toDomain(userEntity)
    
    // Assert
    assertEquals(userEntity.id, userDomain.id)
    assertEquals(userEntity.name, userDomain.name)
    // etc...
}
```

### Tests de tipos especiales 
Para tipos especiales como Drawable, @DrawableRes, @StringRes 
es necesario agregar la anotación @Config al test, de lo contrario lanzará un error.

```kotlin
// example data class
data class SpecialTypeDTO(
    @DrawableRes
    val userImage: Int,
    val userName: Color,
    val userPoster:Drawable,
    @StringRes
    val userNameRes: Int
)

@Config(sdk = [26])
@RunWith(RobolectricTestRunner::class)
class SomeTest {
    @Test
    fun `GIVEN SpecialTypeDTO WHEN fakeData THEN return random data`() {
        val fakeDto = fakeData<SpecialTypeDTO>()

        assertNotNull(fakeDto)
    }

    @Test
    fun `GIVEN SpecialTypeDTO WHEN resource is real THEN should be set in component view`() {
        val fakeDto = fakeData<SpecialTypeDTO>(){
            property(SpecialTypeDTO::userNameRes).returns(R.string.faker_data_class_name)
        }
        val textView = TextView(context)
        textView.setText(fakeDto.userNameRes)
        assertNotNull(textView.text)
    }
}

// example with real resources


```
### Tests de Repositories


## 📋 Requisitos

- Kotlin 1.9.0 o superior
- Android API 26+
- kotlin-reflect (incluido como dependencia)

## 📄 Licencia

Este proyecto está licenciado bajo la licencia MIT, que permite uso libre tanto comercial como no comercial.

```
MIT License

Copyright (c) 2025 Your Name

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

<p align="center">
  Desarrollado para hacer tus tests más simples y efectivos
</p>
