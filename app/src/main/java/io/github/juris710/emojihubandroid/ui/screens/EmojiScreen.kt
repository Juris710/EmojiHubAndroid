package io.github.juris710.emojihubandroid.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.juris710.emojihubandroid.ui.components.EmojiCategoryList
import io.github.juris710.emojihubandroid.ui.components.RandomEmoji

@Composable
fun EmojiScreen(
    emojiUiState: EmojiUiState,
    getRandomEmoji: () -> Unit,
    getAllEmojisOfCategory: (String) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RandomEmoji(
                emojiUiState.randomEmoji,
                getRandomEmoji
            )
            EmojiCategoryList(
                emojiUiState.emojisOfCategory,
                getAllEmojisOfCategory
            )
        }
    }
}