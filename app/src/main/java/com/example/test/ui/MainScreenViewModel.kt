package com.example.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.domain.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    fun getCharacters() {
        _uiState.update { MainScreenUiState.Loading }
        viewModelScope.launch {
            repository.getCharacters()
                .fold(
                    onSuccess = { list -> _uiState.update { MainScreenUiState.Success(list) } },
                    onFailure = { _uiState.update { MainScreenUiState.Error } }
                )
        }
    }
}