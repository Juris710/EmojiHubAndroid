package io.github.juris710.emojihubandroid.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
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
    Box(
        modifier = Modifier.defaultMinSize(100.dp, 100.dp),
        contentAlignment = Alignment.Center
    ) {
        when (randomEmoji) {
            is HttpResult.Loading -> CircularProgressIndicator()
            is HttpResult.Error -> Text(text = randomEmoji.message, color = Color.Red)
            is HttpResult.Success -> EmojiDisplay(randomEmoji.data)
            else -> Text(text = "Press the button below to show random Emoji!")
        }
    }
    val buttonEnabled = randomEmoji !is HttpResult.Loading
    Button(onClick = getRandomEmoji, enabled = buttonEnabled) {
        Text(text = "Show Random Emoji")
    }
}