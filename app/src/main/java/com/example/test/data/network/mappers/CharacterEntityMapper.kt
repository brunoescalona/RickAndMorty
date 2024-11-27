package com.example.test.data.network.mappers

import com.example.test.data.network.models.CharacterEntity
import com.example.test.domain.models.Character

interface CharacterEntityMapper {
    fun map(entity: CharacterEntity): Character
}

class CharacterEntityMapperImpl : CharacterEntityMapper {
    override fun map(entity: CharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            status = when (entity.status) {
                CharacterEntity.Status.Dead -> Character.Status.Dead
                CharacterEntity.Status.Alive -> Character.Status.Alive
                CharacterEntity.Status.Unknown -> Character.Status.Unknown
            },
            species = entity.species,
            type = entity.type,
            gender = when (entity.gender) {
                CharacterEntity.Gender.Male -> Character.Gender.Male
                CharacterEntity.Gender.Female -> Character.Gender.Female
                CharacterEntity.Gender.Genderless -> Character.Gender.Genderless
                CharacterEntity.Gender.Unknown -> Character.Gender.Unknown
            },
            imageUrl = entity.imageUrl,
        )
    }
}