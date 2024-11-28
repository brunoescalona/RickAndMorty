package com.example.test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.CharacterRepositoryImpl
import com.example.test.data.network.CharacterNetworkDataSourceImpl
import com.example.test.data.network.api.CharacterApi
import com.example.test.data.network.mappers.CharacterEntityMapperImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class MainScreenViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    val json = Json { ignoreUnknownKeys = true }
    val retrofit = Retrofit
        .Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("https://rickandmortyapi.com/api/")
        .build()

    val characterApi = retrofit.create(CharacterApi::class.java)
    val dataSource = CharacterNetworkDataSourceImpl(characterApi)
    val mapper = CharacterEntityMapperImpl()
    val repository = CharacterRepositoryImpl(dataSource, mapper)

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