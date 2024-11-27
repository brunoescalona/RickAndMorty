package com.example.test.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.test.domain.models.Character

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainScreenUiState: MainScreenUiState,
    onRetry: () -> Unit,
) {
    when (mainScreenUiState) {
        MainScreenUiState.Error -> MainScreenError(
            onRetry = onRetry,
            modifier = modifier
        )

        MainScreenUiState.Loading -> MainScreenLoading(modifier)
        is MainScreenUiState.Success -> MainScreenSuccess(
            characters = mainScreenUiState.characters, modifier = modifier
        )
    }
}

@Composable
fun MainScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.inversePrimary,
        )
    }
}

@Composable
fun MainScreenError(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Something was wrong, try it again!")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = "try it again".uppercase())
        }
    }
}

@Composable
fun MainScreenSuccess(
    characters: List<Character>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(characters) {
            CharacterItem(character = it)
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                painter = rememberAsyncImagePainter(character.imageUrl),
                contentDescription = null,
            )
            Text(text = character.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenLoadingPreview() {
    MainScreenLoading()
}

@Preview(showBackground = true)
@Composable
fun MainScreenErrorPreview() {
    MainScreenError(onRetry = {})
}

@Preview(showBackground = true)
@Composable
fun MainScreenSuccessPreview() {
    val characters = listOf(
        Character(
            id = 1,
            name = "Some name",
            status = Character.Status.Alive,
            species = "Some species",
            type = "Some type",
            gender = Character.Gender.Genderless,
            imageUrl = "Some image url",
        ),
        Character(
            id = 2,
            name = "Some other name",
            status = Character.Status.Alive,
            species = "Some other species",
            type = "Some other type",
            gender = Character.Gender.Genderless,
            imageUrl = "Some other image url",
        ),
    )
    MainScreenSuccess(characters)
}

@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    val character = Character(
        id = 1,
        name = "Some name",
        status = Character.Status.Alive,
        species = "Some species",
        type = "Some type",
        gender = Character.Gender.Genderless,
        imageUrl = "Some image url",
    )
    CharacterItem(character)
}