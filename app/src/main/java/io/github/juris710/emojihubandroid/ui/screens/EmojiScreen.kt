package io.github.juris710.emojihubandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.github.juris710.emojihubandroid.data.HttpResult
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
            val randomEmoji = emojiUiState.randomEmoji
            val emoji = when (randomEmoji) {
                is HttpResult.Success -> randomEmoji.data
                else -> null
            }
            EmojiDisplay(emoji)
            Button(onClick = {
                emojiViewModel.getRandomEmoji()
            }) {
                Text(text = "Show Random Emoji")
            }
            if (randomEmoji is HttpResult.Error) {
                Text(text = randomEmoji.message, color = Color.Red)
            }
            EmojiCategoryList(
                emojiUiState.emojisOfCategory,
                emojiViewModel::getAllEmojisOfCategory,
            )
        }
    }
}