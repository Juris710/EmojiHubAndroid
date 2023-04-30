package io.github.juris710.emojihubandroid.ui.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.juris710.emojihubandroid.data.Emoji
import io.github.juris710.emojihubandroid.data.HttpResult

@Composable
fun RandomEmoji(
    randomEmoji: HttpResult<Emoji>?,
    getRandomEmoji: () -> Unit
) {
    val emoji = when (randomEmoji) {
        is HttpResult.Success -> randomEmoji.data
        else -> null
    }
    EmojiDisplay(emoji)
    val buttonEnabled = randomEmoji == null || randomEmoji !is HttpResult.Loading
    Button(onClick = getRandomEmoji, enabled = buttonEnabled) {
        Text(text = "Show Random Emoji")
    }
    if (randomEmoji is HttpResult.Error) {
        Text(text = randomEmoji.message, color = Color.Red)
    }
}