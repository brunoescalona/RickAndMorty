package com.example.test.data.network

import com.example.test.data.network.api.CharacterApi
import com.example.test.data.network.models.CharacterResponse

interface CharacterNetworkDataSource {
    suspend fun getCharacterResponse(): Result<CharacterResponse>
}

class CharacterNetworkDataSourceImpl(
    private val api: CharacterApi
) : CharacterNetworkDataSource {
    override suspend fun getCharacterResponse(): Result<CharacterResponse> {
        return try {
            Result.success(api.getCharacters())
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}