package io.github.juris710.emojihubandroid.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.juris710.emojihubandroid.model.Emoji
import io.github.juris710.emojihubandroid.model.HttpResult

@Composable
fun RandomEmoji(
    randomEmoji: HttpResult<Emoji>?,
    getRandomEmoji: () -> Unit
) {
    if (randomEmoji is HttpResult.Loading) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        val emoji = when (randomEmoji) {
            is HttpResult.Success -> randomEmoji.data
            else -> null
        }
        EmojiDisplay(emoji)
    }
    val buttonEnabled = randomEmoji == null || randomEmoji !is HttpResult.Loading
    Button(onClick = getRandomEmoji, enabled = buttonEnabled) {
        Text(text = "Show Random Emoji")
    }
    if (randomEmoji is HttpResult.Error) {
        Text(text = randomEmoji.message, color = Color.Red)
    }
}