package com.example.test.data.network

import com.example.test.data.network.api.CharacterApi
import com.example.test.data.network.models.CharacterEntity
import com.example.test.data.network.models.CharacterResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class CharacterNetworkDataSourceImplTest {

    private val characterApi: CharacterApi = mock()
    private val dataSource: CharacterNetworkDataSource = CharacterNetworkDataSourceImpl(
        api = characterApi,
    )

    @Test
    @DisplayName("Test the network data source when the service is failing")
    fun testFailure() = runTest {
        val error = IllegalStateException()
        given(characterApi.getCharacters()).willThrow(error)

        val actual = dataSource.getCharacterResponse()

        assertEquals(actual, Result.failure<Throwable>(error))
    }

    @Test
    @DisplayName("Test the network data source when the service is responding successfully")
    fun testSuccess() = runTest {
        val characters = listOf(
            CharacterEntity(
                id = 1,
                name = "Some name",
                status = CharacterEntity.Status.Alive,
                species = "Some species",
                type = "Some type",
                gender = CharacterEntity.Gender.Genderless,
                imageUrl = "Some image url",
            ),
            CharacterEntity(
                id = 2,
                name = "Some other name",
                status = CharacterEntity.Status.Alive,
                species = "Some other species",
                type = "Some other type",
                gender = CharacterEntity.Gender.Genderless,
                imageUrl = "Some other image url",
            ),
        )
        val response = CharacterResponse(results = characters)
        given(characterApi.getCharacters()).willReturn(response)

        val actual = dataSource.getCharacterResponse()

        assertEquals(actual, Result.success(response))
    }
}