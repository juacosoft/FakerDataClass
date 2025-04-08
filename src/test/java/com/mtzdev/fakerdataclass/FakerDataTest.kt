package com.mtzdev.fakerdataclass

import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.mtzdev.fakerdataclass.core.fakeData
import com.mtzdev.fakerdataclass.dataclass.CompleteTestDto
import com.mtzdev.fakerdataclass.dataclass.SimpleTestDTO
import com.mtzdev.fakerdataclass.dataclass.SpecialTypeDTO
import com.mtzdev.fakerdataclass.dataclass.UserTestDTO
import com.mtzdev.fakerdataclass.dataclass.UserType
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(
    sdk = [26],
    manifest = Config.NONE,
    packageName = "com.mtzdev.fakerdataclass"
)
@RunWith(RobolectricTestRunner::class)
class FakerDataTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = Robolectric.buildActivity(Activity::class.java).get()
    }

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

    @Test
    fun `GIVEN SpecialTypeDTO WHEN fakeData THEN return random data`() {
        val fakeDto = fakeData<SpecialTypeDTO>(){
            property(SpecialTypeDTO::userNameRes).returns(R.string.faker_data_class_name)
        }
        val imageView = ImageView(context)
        val textView = TextView(context)

        imageView.setImageDrawable(fakeDto.userPoster)
        textView.setText(fakeDto.userNameRes)

        assertNotNull(fakeDto)
        assert(imageView.drawable == fakeDto.userPoster)
        assertNotNull(textView.text)
        println(fakeDto)
    }
}

