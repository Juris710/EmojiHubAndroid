package io.github.juris710.emojihubandroid.ui.components

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.github.juris710.emojihubandroid.Emoji
import io.github.juris710.emojihubandroid.RetrofitInstance
import io.github.juris710.emojihubandroid.handleHttpRequest

private val categories = listOf(
    "smileys-and-people",
    "animals-and-nature",
    "food-and-drink",
    "travel-and-places",
    "activities",
    "objects",
    "symbols",
    "flags"
)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChips(
    selectedCategory: String,
    setSelectedCategory: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        categories.forEach { category ->
            val selected = selectedCategory == category
            FilterChip(
                selected = selected,
                onClick = { setSelectedCategory(category) },
                colors = if (selected) ChipDefaults.filterChipColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary
                ) else ChipDefaults.filterChipColors(),
                modifier = Modifier.padding(4.dp)
            ) {
                Text(category)
            }
        }
    }
}

@Composable
fun EmojiCategoryList() {
    val context = LocalContext.current
    var selectedCategory by remember {
        mutableStateOf("")
    }
    var emojis by remember {
        mutableStateOf<List<Emoji>>(listOf())
    }
    val scrollState = rememberScrollState()
    LaunchedEffect(selectedCategory) {
        if (selectedCategory == "") {
            return@LaunchedEffect
        }
        try {
            val newEmojis = handleHttpRequest {
                RetrofitInstance.api.getAllEmojisOfCategory(selectedCategory)
            }
            emojis = newEmojis ?: listOf()
        } catch (e: Exception) {
            Toast.makeText(context, e.message.orEmpty(), Toast.LENGTH_LONG).show()
        }
        scrollState.scrollTo(0)
    }
    CategoryChips(selectedCategory) { selectedCategory = it }
    LazyColumn {
        items(emojis.size) {
            EmojiDisplay(emoji = emojis[it])
        }
    }
}