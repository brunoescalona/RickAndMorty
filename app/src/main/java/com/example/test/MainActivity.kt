package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.test.ui.MainScreen
import com.example.test.ui.MainScreenViewModel
import com.example.test.ui.theme.TestTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainScreenViewModel: MainScreenViewModel by viewModels()

        setContent {
            TestTheme {
                val mainUiState by mainScreenViewModel.uiState.collectAsState()
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    mainScreenUiState = mainUiState,
                    onRetry = mainScreenViewModel::getCharacters
                )
            }
        }
    }
}