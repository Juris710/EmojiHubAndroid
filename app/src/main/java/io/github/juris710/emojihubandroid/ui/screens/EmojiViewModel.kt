package io.github.juris710.emojihubandroid.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.juris710.emojihubandroid.Emoji
import io.github.juris710.emojihubandroid.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException

data class EmojiUiState(
    val randomEmoji: Emoji? = null,
    val emojisOfCategory: List<Emoji> = listOf(),
    val errorMessage: String = ""
)

class EmojiViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EmojiUiState())
    val uiState get() = _uiState.asStateFlow()
    fun getRandomEmoji() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getRandomEmoji()
                if (!response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            randomEmoji = null,
                            errorMessage = "Request Failed with status code ${response.code()}"
                        )
                    }
                    return@launch
                }
                val emoji = response.body()
                if (emoji == null) {
                    _uiState.update {
                        it.copy(randomEmoji = null, errorMessage = "Response body is empty")
                    }
                } else {
                    _uiState.update {
                        it.copy(randomEmoji = emoji, errorMessage = "")
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is IOException -> "You might not have Internet connection."
                    is HttpException -> "Request Failed with status code ${e.code()}"
                    else -> "Unknown Error"
                }
                _uiState.update {
                    it.copy(randomEmoji = null, errorMessage = errorMessage)
                }
            }
        }
    }

    fun getEmojisOfCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getAllEmojisOfCategory(category)
                if (!response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            emojisOfCategory = listOf(),
                            errorMessage = "Request Failed with status code ${response.code()}"
                        )
                    }
                }
                val emojis = response.body()
                if (emojis == null) {
                    _uiState.update {
                        it.copy(
                            emojisOfCategory = listOf(),
                            errorMessage = "Response body is empty"
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            emojisOfCategory = emojis,
                            errorMessage = ""
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is IOException -> "You might not have Internet connection."
                    is HttpException -> "Request Failed with status code ${e.code()}"
                    else -> "Unknown Error"
                }
                _uiState.update {
                    it.copy(emojisOfCategory = listOf(), errorMessage = errorMessage)
                }
            }
        }
    }
}