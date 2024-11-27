package com.example.test.domain

import com.example.test.domain.models.Character

interface CharacterRepository {
    suspend fun getCharacters(): Result<List<Character>>
}