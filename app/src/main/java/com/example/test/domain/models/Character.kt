package com.example.test.domain.models

data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: Gender,
    val imageUrl: String,
) {
    enum class Status { Dead, Alive, Unknown }

    enum class Gender { Male, Female, Genderless, Unknown }
}
