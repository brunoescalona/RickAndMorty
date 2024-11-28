package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.test.data.CharacterRepositoryImpl
import com.example.test.data.network.CharacterNetworkDataSourceImpl
import com.example.test.data.network.api.CharacterApi
import com.example.test.data.network.mappers.CharacterEntityMapperImpl
import com.example.test.ui.MainScreen
import com.example.test.ui.MainScreenUiState
import com.example.test.ui.theme.TestTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


class MainActivity : ComponentActivity() {
    private val uiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Loading)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        lifecycleScope.launch {
            repository.getCharacters()
                .fold(
                    onSuccess = { list -> uiState.update { MainScreenUiState.Success(list) } },
                    onFailure = { uiState.update { MainScreenUiState.Error } }
                )
        }

        setContent {
            TestTheme {
                val mainUiState by uiState.collectAsState()
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainScreenUiState = mainUiState,
                    onRetry = {}
                )
            }
        }
    }
}