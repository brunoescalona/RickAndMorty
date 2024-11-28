package com.example.test.data.network.mappers

import com.example.test.data.network.models.CharacterEntity
import com.example.test.domain.models.Character
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.jupiter.api.DisplayName

@DisplayName("Mapper test")
class CharacterEntityMapperImplTest {
    private val mapper = CharacterEntityMapperImpl()

    @Test
    @DisplayName("Tet the map function from entity to domain")
    fun testMapper() {
        val entity = CharacterEntity(
            id = 1,
            name = "Some name",
            status = CharacterEntity.Status.Alive,
            species = "Some species",
            type = "Some type",
            gender = CharacterEntity.Gender.Genderless,
            imageUrl = "Some image url",
        )

        val expected = Character(
            id = 1,
            name = "Some name",
            status = Character.Status.Alive,
            species = "Some species",
            type = "Some type",
            gender = Character.Gender.Genderless,
            imageUrl = "Some image url",
        )

       val actual = mapper.map(entity)

        assertEquals(expected, actual)
    }
}