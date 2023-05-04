package io.github.juris710.emojihubandroid.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.github.juris710.emojihubandroid.R
import io.github.juris710.emojihubandroid.ui.components.EmojiCategoryList
import io.github.juris710.emojihubandroid.ui.components.RandomEmoji

@Composable
fun EmojiScreen(
    emojiUiState: EmojiUiState,
    getRandomEmoji: () -> Unit,
    selectEmojiCategory: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = {
                        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    }) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = stringResource(R.string.description_show_licenses_icon)
                        )
                    }
                }
            )
            RandomEmoji(
                emojiUiState.randomEmoji,
                getRandomEmoji
            )
            EmojiCategoryList(
                emojiUiState.selectedEmojiCategory,
                selectEmojiCategory,
                emojiUiState.emojisOfCategory,
            )
        }
    }
}