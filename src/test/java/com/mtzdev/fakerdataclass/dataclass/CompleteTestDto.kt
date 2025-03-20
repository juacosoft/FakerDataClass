package com.mtzdev.fakerdataclass.dataclass

import java.util.Date

data class CompleteTestDto(
    val user: UserTestDTO,
    val atributes: List<AditionalUserInfoDTO>,
    val type: UserType,
    val active: Boolean,
    val birthDate: Date,
    val createAt: Date
)

data class UserTestDTO(
    val name: String,
    val age: Int,
    val email: String
)

data class AditionalUserInfoDTO(
    val address: String,
    val phone: String
)

enum class UserType {
    ADMIN,
    USER
}