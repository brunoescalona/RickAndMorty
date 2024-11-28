package com.example.test.data

import com.example.test.data.network.CharacterNetworkDataSource
import com.example.test.data.network.mappers.CharacterEntityMapper
import com.example.test.domain.CharacterRepository
import com.example.test.domain.models.Character
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterNetworkDataSource: CharacterNetworkDataSource,
    private val characterEntityMapper: CharacterEntityMapper,
) : CharacterRepository {
    override suspend fun getCharacters(): Result<List<Character>> {
        return characterNetworkDataSource
            .getCharacterResponse()
            .mapCatching { characterResponse ->
                characterResponse.results.map { characterEntityMapper.map(it) }
            }
    }
}