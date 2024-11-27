package com.example.test.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterEntity(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("status") val status: Status,
    @SerialName("species") val species: String,
    @SerialName("type") val type: String,
    @SerialName("gender") val gender: Gender,
    @SerialName("image") val imageUrl: String
) {
    @Serializable
    enum class Status {
        @SerialName("Dead")
        Dead,
        @SerialName("Alive")
        Alive,
        @SerialName("unknown")
        Unknown
    }

    enum class Gender {
        @SerialName("Male")
        Male,
        @SerialName("Female")
        Female,
        @SerialName("Genderless")
        Genderless,
        @SerialName("unknown")
        Unknown
    }
}
