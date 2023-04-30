package io.github.juris710.emojihubandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.juris710.emojihubandroid.ui.screens.EmojiScreen
import io.github.juris710.emojihubandroid.ui.screens.EmojiViewModel
import io.github.juris710.emojihubandroid.ui.theme.EmojiHubAndroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val emojiViewModel: EmojiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojiHubAndroidTheme {
                EmojiScreen(emojiViewModel)
            }
        }
    }
}