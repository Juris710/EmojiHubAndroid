package io.github.juris710.emojihubandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import io.github.juris710.emojihubandroid.ui.components.EmojiCategoryList
import io.github.juris710.emojihubandroid.ui.components.EmojiDisplay
import io.github.juris710.emojihubandroid.ui.theme.EmojiHubAndroidTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojiHubAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var displayedEmoji by remember {
                            mutableStateOf<Emoji?>(null)
                        }
                        var errorMessage by remember {
                            mutableStateOf("")
                        }
                        EmojiDisplay(displayedEmoji)
                        Button(onClick = {
                            lifecycleScope.launch {
                                try {
                                    val emoji =
                                        handleHttpRequest(RetrofitInstance.api::getRandomEmoji)
                                    if (emoji == null) {
                                        errorMessage = "No emoji found"
                                    }
                                    displayedEmoji = emoji
                                    errorMessage = ""
                                } catch (e: Exception) {
                                    errorMessage = e.message ?: "Unknown Error"
                                }
                            }
                        }) {
                            Text(text = "Show Random Emoji")
                        }
                        if (errorMessage.isNotEmpty()) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                        EmojiCategoryList()
                    }
                }
            }
        }
    }
}