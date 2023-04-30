package io.github.juris710.emojihubandroid.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.juris710.emojihubandroid.data.Emoji
import io.github.juris710.emojihubandroid.data.EmojiRepository
import io.github.juris710.emojihubandroid.data.HttpResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EmojiUiState(
    val randomEmoji: HttpResult<Emoji>? = null,
    val emojisOfCategory: HttpResult<List<Emoji>> = HttpResult.Success(listOf()),
//    val errorMessage: String = ""
)

@HiltViewModel
class EmojiViewModel @Inject constructor(
    private val repository: EmojiRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(EmojiUiState())
    val uiState get() = _uiState.asStateFlow()
    fun getRandomEmoji() {
        _uiState.update {
            it.copy(randomEmoji = HttpResult.Loading())
        }
        viewModelScope.launch {
            _uiState.update {
                it.copy(randomEmoji = repository.getRandomEmoji())
            }
        }
    }

    fun getAllEmojisOfCategory(category: String) {
        _uiState.update {
            it.copy(emojisOfCategory = HttpResult.Loading())
        }
        viewModelScope.launch {
            _uiState.update {
                it.copy(emojisOfCategory = repository.getAllEmojisOfCategory(category))
            }
        }
    }
}