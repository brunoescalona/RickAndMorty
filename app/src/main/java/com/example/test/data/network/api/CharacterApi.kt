package com.example.test.data.network.api

import com.example.test.data.network.models.CharacterResponse
import retrofit2.http.GET


interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}