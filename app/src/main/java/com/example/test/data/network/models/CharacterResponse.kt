package com.example.test.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//Taken from https://rickandmortyapi.com/documentation/#character-schema
@Serializable
data class CharacterResponse (
    @SerialName("results") val results: List<CharacterEntity>
)