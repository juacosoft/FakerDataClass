package com.mtzdev.fakerdataclass

import com.mtzdev.fakerdataclass.core.fakeData
import com.mtzdev.fakerdataclass.dataclass.CompleteTestDto
import com.mtzdev.fakerdataclass.dataclass.SimpleTestDTO
import com.mtzdev.fakerdataclass.dataclass.UserTestDTO
import com.mtzdev.fakerdataclass.dataclass.UserType
import junit.framework.TestCase.assertNotNull
import org.junit.Test

class FakerDataTest {

    @Test
    fun `WHEN SimpleTestDTO THEN fakeData return random data`() {
        val fakeDto = fakeData<SimpleTestDTO>()

        assertNotNull(fakeDto)
        println(fakeDto)
    }

    @Test
    fun `GIVEN SimpleTestDTO with custom values WHEN fakeData THEN return custom data`() {
        val fakeDto = fakeData<SimpleTestDTO>(
            "name" to "John Doe",
            "age" to 30,
            "email" to "some@mail.com"
        )

        assert(fakeDto.name == "John Doe")
        assert(fakeDto.age == 30)
        assert(fakeDto.email == "some@mail.com")
    }

    @Test
    fun `GIVEN SimpleTestDTO WHEN using DSL property THEN return custom data`() {
        val fakeDto = fakeData<SimpleTestDTO> {
            property(SimpleTestDTO::name).returns("John Doe")
            property(SimpleTestDTO::age).returns(30)
            property(SimpleTestDTO::email).returns("some@mail.com")
        }

        assert(fakeDto.name == "John Doe")
        assert(fakeDto.age == 30)
        assert(fakeDto.email == "some@mail.com")
    }

    @Test
    fun `GIVEN CompleteTestDto WHEN fakeData THEN return random data`() {
        val fakeDto = fakeData<CompleteTestDto>()

        assertNotNull(fakeDto)
        println(fakeDto)
    }

    @Test
    fun `GIVEN CompleteTestDto WHEN using DSL property THEN return custom data`() {
        val userTestDTO = fakeData<UserTestDTO>{
            property(UserTestDTO::name).returns("John Doe")
            property(UserTestDTO::age).returns(30)
            property(UserTestDTO::email).returns("some@mail.com")
        }
        val fakeDto = fakeData<CompleteTestDto> {
            property(CompleteTestDto::user).returns(userTestDTO)
            property(CompleteTestDto::type).returns(UserType.ADMIN)
        }

        assert(fakeDto.user.name == "John Doe")
        assert(fakeDto.user.age == 30)
        assert(fakeDto.user.email == "some@mail.com")
        assert(fakeDto.atributes.isNotEmpty())
        assert(fakeDto.type == UserType.ADMIN)
        println(fakeDto)
    }
}

