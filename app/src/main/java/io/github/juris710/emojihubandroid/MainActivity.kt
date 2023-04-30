package io.github.juris710.emojihubandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.juris710.emojihubandroid.ui.screens.EmojiScreen
import io.github.juris710.emojihubandroid.ui.screens.EmojiViewModel
import io.github.juris710.emojihubandroid.ui.theme.EmojiHubAndroidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojiHubAndroidTheme {
                EmojiScreen(
                    emojiViewModel = EmojiViewModel()
                )
            }
        }
    }
}