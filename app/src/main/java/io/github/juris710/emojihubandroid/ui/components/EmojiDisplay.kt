package io.github.juris710.emojihubandroid.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.juris710.emojihubandroid.Emoji

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
        Text(
            text = text,
            minLines = 1,
            fontSize = 64.sp,
            modifier = Modifier
                .padding(16.dp)
                .defaultMinSize(64.dp, 64.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()
        ) {
            Text("Name : $name")
            Text("Category : $category")
            Text("Group : $group")
        }
    }
}