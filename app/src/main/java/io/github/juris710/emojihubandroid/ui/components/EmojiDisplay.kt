package io.github.juris710.emojihubandroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import io.github.juris710.emojihubandroid.Emoji

private val EMOJI_TEXT_WIDTH = 64.dp
private val EMOJI_TEXT_HEIGHT = 64.dp

@Composable
fun EmojiDisplay(emoji: Emoji?) {
    val text = emoji?.emojiAsString ?: ""
    val name = emoji?.name.orEmpty()
    val category = emoji?.category.orEmpty()
    val group = emoji?.group.orEmpty()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .size(EMOJI_TEXT_WIDTH, EMOJI_TEXT_HEIGHT),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = LocalDensity.current.run {
                    val textSize = min(EMOJI_TEXT_WIDTH * 1.7f, EMOJI_TEXT_HEIGHT)
                    val fontSizeDp = textSize * 0.6f
                    fontSizeDp.toSp()
                },
            )
        }
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()
        ) {
            Text("Name : $name")
            Text("Category : $category")
            Text("Group : $group")
        }
    }
}