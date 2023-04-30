package io.github.juris710.emojihubandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.juris710.emojihubandroid.ui.components.EmojiCategoryList
import io.github.juris710.emojihubandroid.ui.components.RandomEmoji

@Composable
fun EmojiScreen(
    emojiViewModel: EmojiViewModel
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val emojiUiState by emojiViewModel.uiState.collectAsState()
            RandomEmoji(
                emojiUiState.randomEmoji,
                emojiViewModel::getRandomEmoji
            )
            EmojiCategoryList(
                emojiUiState.emojisOfCategory,
                emojiViewModel::getAllEmojisOfCategory,
            )
        }
    }
}