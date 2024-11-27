package com.example.test.ui

import com.example.test.domain.models.Character

sealed class MainScreenUiState {
    data object Loading: MainScreenUiState()
    data object Error: MainScreenUiState()
    data class Success(val characters: List<Character>): MainScreenUiState()
}