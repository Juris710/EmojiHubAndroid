package io.github.juris710.emojihubandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import io.github.juris710.emojihubandroid.ui.theme.EmojiHubAndroidTheme
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

private suspend fun <T> handleHttpRequest(exec: suspend () -> Response<T>): T? {
    val response = try {
        exec()
    } catch (e: Exception) {
        Timber.e(e)
        val errorMessage = when (e) {
            is IOException -> "You might not have Internet connection."
            is HttpException -> "Request Failed with status code ${e.code()}"
            else -> "Unknown Error"
        }
        throw Exception(errorMessage)
    }
    val body = response.body()
    if (!response.isSuccessful) {
        throw Exception("Request failed with status code ${response.code()}")
    }
    return body
}

class MainActivity : ComponentActivity() {
    private fun unicodeFromCodePoints(codePoints: List<Int>): String {
        val builder = StringBuilder()
        codePoints.forEach {
            builder.appendCodePoint(it)
        }
        return builder.toString()
    }

    @JvmName("unicodeFromCodePointStrings")
    private fun unicodeFromCodePoints(codePoints: List<String>): String {
        return unicodeFromCodePoints(codePoints.map {
            if (!it.startsWith("U+")) {
                throw IllegalArgumentException("Invalid codepoint $it")
            }
            it.substring(2).toInt(16)
        })
    }

    @Composable
    fun EmojiDisplay(emoji: Emoji?) {
        val text = if (emoji != null) try {
            unicodeFromCodePoints(emoji.unicode)
        } catch (_: Exception) {
            "?"
        } else ""
        val name = emoji?.name ?: ""
        val category = emoji?.category ?: ""
        val group = emoji?.group ?: ""
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
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text("Name : $name")
                Text("Category : $category")
                Text("Group : $group")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmojiHubAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
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
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EmojiHubAndroidTheme {
        Greeting("Android")
    }
}