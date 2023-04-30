package io.github.juris710.emojihubandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.juris710.emojihubandroid.ui.components.EmojiCategoryList
import io.github.juris710.emojihubandroid.ui.components.EmojiDisplay

@Composable
fun EmojiScreen(
    emojiViewModel: EmojiViewModel
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emojiUiState by emojiViewModel.uiState.collectAsState()
            EmojiDisplay(emojiUiState.randomEmoji)
            Button(onClick = {
                emojiViewModel.getRandomEmoji()
            }) {
                Text(text = "Show Random Emoji")
            }
            val errorMessage = emojiUiState.errorMessage
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
            }
            EmojiCategoryList(
                emojiUiState.emojisOfCategory,
                emojiViewModel::getEmojisOfCategory,
            )
        }
    }
}