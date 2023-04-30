package io.github.juris710.emojihubandroid.data

import dagger.hilt.android.scopes.ActivityScoped
import io.github.juris710.emojihubandroid.R
import io.github.juris710.emojihubandroid.model.Emoji
import io.github.juris710.emojihubandroid.model.HttpResult
import io.github.juris710.emojihubandroid.model.UiText
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.CancellationException
import javax.inject.Inject

@ActivityScoped
class EmojiRepository @Inject constructor(
    private val api: EmojiHubApi
) {
    private suspend fun <T> wrapHttpRequest(
        execHttpRequest: suspend () -> T
    ): HttpResult<T> {
        return try {
            val response = execHttpRequest()
            HttpResult.Success(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            HttpResult.Error(
                when (e) {
                    is IOException -> UiText.StringResource(R.string.errorMessage_ioException)
                    is HttpException -> UiText.StringResource(
                        R.string.errorMessage_httpException,
                        e.code()
                    )
                    else -> UiText.StringResource(R.string.errorMessage_unknown)
                }
            )
        }
    }

    suspend fun getRandomEmoji(): HttpResult<Emoji> {
        return wrapHttpRequest {
            api.getRandomEmoji()
        }
    }

    suspend fun getAllEmojisOfCategory(category: String): HttpResult<List<Emoji>> {
        return wrapHttpRequest {
            api.getAllEmojisOfCategory(category)
        }
    }
}